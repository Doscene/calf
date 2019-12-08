package com.github.doscene.calf.ftp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * <h1>com.github.doscene.calf.ftp</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Data
@Slf4j
public class FtpClientConfiguration implements Serializable {
    private String host;
    private int port;
    private String username;
    private String password;
    private boolean passiveMode;
    private String encoding;
    private int clientTimeout;
    private int threadNum;
    private int transferFileType;
    private boolean renameUploaded;
    private int retryTimes;
    private int bufferSize;
    private String workingDirectory;
}
