package com.asiafrank.learn.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Main
 * <p>
 * </p>
 * Created at 4/6/2017.
 *
 * @author zhangxf
 */
@ServletComponentScan(basePackages = "com.asiafrank.learn.springboot")
@SpringBootApplication(scanBasePackages = "com.asiafrank.learn.springboot")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
