package com.xgstudio.springbootdemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 邮件服务器相关的参数
 * @author chenxsa
 */
@Component
@ConfigurationProperties(prefix = "springbootdemo.email")
public class EmailProperties {
    private String server;
    private String port;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
