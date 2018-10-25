package com.asiafrank.learn.provider;


import com.asiafrank.learn.core.HelloService;

/**
 * @author zhangxf created at 10/24/2018.
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String string) {
        System.out.println("Server receive: " + string);
        return "hello " + string + " ！";
    }
}
