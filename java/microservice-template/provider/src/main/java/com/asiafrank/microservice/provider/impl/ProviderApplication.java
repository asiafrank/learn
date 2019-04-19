package com.asiafrank.microservice.provider.impl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author zhangxiaofan 2019/04/18-20:52
 */
@SpringBootApplication
@ImportResource(value = {"classpath:hsf-provider.xml"})
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
