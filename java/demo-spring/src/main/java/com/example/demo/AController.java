package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
public class AController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello() throws InterruptedException {
//        System.out.println(Thread.currentThread().getName() + " start," + new Date());

//        TimeUnit.SECONDS.sleep(10);

//        System.out.println(Thread.currentThread().getName() + " end," + new Date());
        return "hello";
    };
}
