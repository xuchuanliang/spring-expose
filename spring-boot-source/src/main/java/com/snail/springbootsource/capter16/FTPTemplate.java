package com.snail.springbootsource.capter16;

public class FTPTemplate {
    private FTPInfo ftpInfo;

    public FTPTemplate(FTPInfo ftpInfo) {
        this.ftpInfo = ftpInfo;
    }

    public Object execute(FTPCallback ftpCallback) {
        ftpInfo.login();
        ftpCallback.processCallback(ftpInfo);
        ftpInfo.release();
        return null;
    }
}
