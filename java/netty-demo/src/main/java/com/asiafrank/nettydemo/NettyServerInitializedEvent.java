package com.asiafrank.nettydemo;

import org.springframework.boot.web.server.WebServer;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * @author zhangxiaofan 2020/11/28-15:12
 */
public class NettyServerInitializedEvent extends ApplicationContextEvent {
    private final WebServer webServer;

    protected NettyServerInitializedEvent(WebServer webServer, NettyServerApplicationContext applicationContext) {
        super(applicationContext);
        this.webServer = webServer;
    }

    public WebServer getWebServer() {
        return webServer;
    }
}
