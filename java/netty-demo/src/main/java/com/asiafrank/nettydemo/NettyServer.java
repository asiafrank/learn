package com.asiafrank.nettydemo;

import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.server.WebServerException;

/**
 * @author zhangxiaofan 2020/11/28-14:56
 */
public class NettyServer implements WebServer {

    // TODO: 补充完整 netty 的启动逻辑

    @Override
    public void start() throws WebServerException {

    }

    @Override
    public void stop() throws WebServerException {

    }

    @Override
    public int getPort() {
        return 0;
    }
}
