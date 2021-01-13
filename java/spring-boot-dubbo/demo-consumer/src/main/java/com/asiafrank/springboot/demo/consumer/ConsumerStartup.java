package com.asiafrank.springboot.demo.consumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableDubbo
@PropertySource(value = "classpath:/dubbo-consumer.properties")
public class ConsumerStartup {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerStartup.class, args);
    }
}