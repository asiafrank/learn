package com.asiafrank.nettydemo;

import org.springframework.boot.web.server.WebServer;

/**
 * @author zhangxiaofan 2020/11/28-15:54
 */
public class NettyServerFactory extends AbstractConfigurableWebServerFactory {

    private WebServer webServer;

    public WebServer getWebServer() {
        // TODO: create webserver
        return this.webServer;
    }
}
