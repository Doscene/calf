package com.github.doscene.calf.ftp;

import com.alibaba.fastjson.JSON;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <h1>com.github.doscene.calf.ftp</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Setter
@Slf4j
public class FtpClientFactory implements PooledObjectFactory<FTPClient> {

    private FtpClientConfiguration configuration;

    public FtpClientFactory() {
        if (null == configuration) {
            Properties prop = new Properties();
            try (InputStream inS = FtpClientPool.class.getResourceAsStream("/ftp.properties")) {
                prop.load(inS);
                log.info("加载FTP连接池配置成功");
                log.debug(JSON.toJSONString(prop));
            } catch (IOException e) {
                log.error("加载FTP连接池失败");
                e.printStackTrace();
                throw new IllegalArgumentException("ftp.properties文件加载失败");
            }
            configuration = new FtpClientConfiguration();
            configuration.setBufferSize(Integer.parseInt(prop.getProperty("ftpClient_bufferSize")));
            configuration.setClientTimeout(Integer.parseInt(prop.getProperty("ftpClient_clientTimeout")));
            configuration.setEncoding(prop.getProperty("ftpClient_encoding"));
            configuration.setHost(prop.getProperty("ftpClient_host"));
            configuration.setUsername(prop.getProperty("ftpClient_username"));
            configuration.setPassword(prop.getProperty("ftpClient_password"));
            configuration.setPort(Integer.parseInt(prop.getProperty("ftpClient_port")));
            configuration.setPassiveMode(Boolean.parseBoolean(prop.getProperty("ftpClient_passiveMode")));
            configuration.setThreadNum(Integer.parseInt(prop.getProperty("ftpClient_threadNum")));
            configuration.setTransferFileType(Integer.parseInt(prop.getProperty("ftpClient_transferFileType")));
            configuration.setRenameUploaded(Boolean.parseBoolean(prop.getProperty("ftpClient_renameUploaded")));
            configuration.setRetryTimes(Integer.parseInt(prop.getProperty("ftpClient_retryTimes")));
            configuration.setWorkingDirectory(prop.getProperty("ftpClient_workingDirectory"));
            log.error("配置装配完成");
        }
    }

    @Override
    public PooledObject<FTPClient> makeObject() throws Exception {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(configuration.getClientTimeout());
        try {
            ftpClient.connect(configuration.getHost(), configuration.getPort());
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                log.warn("FTPServer refused connection");
                return null;
            }
            boolean result = ftpClient.login(configuration.getUsername(), configuration.getPassword());
            if (!result) {
                throw new Exception("ftpClient登陆失败! userName:" + configuration.getUsername() + " ; password:" + configuration.getPassword());
            }
            ftpClient.setFileType(configuration.getTransferFileType());
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding(configuration.getEncoding());
            if (configuration.isPassiveMode()) {
                ftpClient.enterLocalPassiveMode();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new DefaultPooledObject<>(ftpClient);
    }

    @Override
    public void destroyObject(PooledObject<FTPClient> p) throws Exception {
        FTPClient ftpClient = p.getObject();
        try {
            if (ftpClient != null && ftpClient.isConnected()) {
                ftpClient.logout();
            }
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            // 注意,一定要在finally代码中断开连接，否则会导致占用ftp连接情况
            try {
                if (ftpClient != null) {
                    ftpClient.disconnect();
                }
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }

    @Override
    public boolean validateObject(PooledObject<FTPClient> p) {
        try {
            return p.getObject().sendNoOp();
        } catch (IOException e) {
            throw new RuntimeException("Failed to validate client: " + e, e);
        }
    }

    @Override
    public void activateObject(PooledObject<FTPClient> p) throws Exception {

    }

    @Override
    public void passivateObject(PooledObject<FTPClient> p) throws Exception {

    }
}
