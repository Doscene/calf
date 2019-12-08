package com.github.doscene.calf.ftp;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * <h1>com.github.doscene.calf.ftp</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Slf4j
@Setter
public class FtpClientPool implements ObjectPool<FTPClient> {

    private GenericObjectPoolConfig poolConfig;
    private String location;
    private GenericObjectPool<FTPClient> proxied;
    private FtpClientFactory ftpClientFactory;
    private FtpClientConfiguration configuration;
    private static final String DEFAULT_LOCATION = "ftp.properties";

    public FtpClientPool() throws IOException {
        this(FtpClientPool.DEFAULT_LOCATION);
    }

    public FtpClientPool(String location) throws IOException {
        this.location = location;
        Properties prop = new Properties();
        try (InputStream inS = FtpClientPool.class.getResourceAsStream("/" + this.location)) {
            prop.load(inS);
            log.info("加载FTP连接池配置成功");
            log.debug(JSON.toJSONString(prop));
        } catch (IOException e) {
            log.error("加载FTP连接池失败");
            e.printStackTrace();
            throw e;
        }
        try {
            poolConfig = new GenericObjectPoolConfig();
            poolConfig.setBlockWhenExhausted(Boolean.parseBoolean(prop.getProperty("ftpClient_blockWhenExhausted")));
            poolConfig.setMaxWaitMillis(Long.parseLong(prop.getProperty("ftpClient_maxWait")));
            poolConfig.setMinIdle(Integer.parseInt(prop.getProperty("ftpClient_minIdle")));
            poolConfig.setMaxIdle(Integer.parseInt(prop.getProperty("ftpClient_maxIdle")));
            poolConfig.setMaxTotal(Integer.parseInt(prop.getProperty("ftpClient_maxTotal")));
            poolConfig.setTestOnBorrow(Boolean.parseBoolean(prop.getProperty("ftpClient_testOnBorrow")));
            poolConfig.setTestOnReturn(Boolean.parseBoolean(prop.getProperty("ftpClient_testOnReturn")));
            poolConfig.setTestOnCreate(Boolean.parseBoolean(prop.getProperty("ftpClient_testOnCreate")));
            poolConfig.setTestWhileIdle(Boolean.parseBoolean(prop.getProperty("ftpClient_testWhileIdle")));
            poolConfig.setLifo(Boolean.parseBoolean(prop.getProperty("ftpClient_lifo")));

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
        } catch (Exception e) {
            log.error("配置装配失败,失败原因【{}】", e.getMessage());
            e.printStackTrace();
            throw e;
        }

        ftpClientFactory = new FtpClientFactory();
        ftpClientFactory.setConfiguration(configuration);
        this.proxied = new GenericObjectPool<>(ftpClientFactory);
    }

    @Override
    public FTPClient borrowObject() throws Exception, NoSuchElementException, IllegalStateException {
        return proxied.borrowObject();
    }

    @Override
    public void returnObject(FTPClient obj) throws Exception {
        proxied.returnObject(obj);
    }

    @Override
    public void invalidateObject(FTPClient obj) throws Exception {
        proxied.invalidateObject(obj);
    }

    @Override
    public void addObject() throws Exception, IllegalStateException, UnsupportedOperationException {
        proxied.addObject();
    }

    @Override
    public int getNumIdle() {
        return proxied.getNumIdle();
    }

    @Override
    public int getNumActive() {
        return proxied.getNumActive();
    }

    @Override
    public void clear() throws Exception, UnsupportedOperationException {
        proxied.clear();
    }

    @Override
    public void close() {
        proxied.close();
    }
}
