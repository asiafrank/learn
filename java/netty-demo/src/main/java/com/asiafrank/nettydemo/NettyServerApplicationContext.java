package com.asiafrank.nettydemo;

import org.springframework.boot.web.server.WebServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangxiaofan 2020/11/28-15:06
 */
public class NettyServerApplicationContext extends AnnotationConfigApplicationContext {

    private volatile WebServer nettyServer;

    // TODO:
}
