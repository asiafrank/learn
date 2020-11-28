package com.asiafrank.nettydemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangxiaofan 2020/11/28-16:05
 */
@Configuration
public class NettyServerConfiguration {

    @Bean
    public NettyServerFactory nettyServerFactory() {
        return new NettyServerFactory();
    }
}
