package com.asiafrank.springboot.demo.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

/**
 * @author zhangxiaofan 2021/01/13-10:44
 */

@Configuration
@EnableDubbo(scanBasePackages = "com.asiafrank.springboot.demo.provider")
@PropertySource("classpath:/dubbo-provider.properties")
public class ProviderStartup {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ProviderStartup.class);
        context.refresh();
        System.out.println("DemoService provider is starting...");
        System.in.read();
    }
}
