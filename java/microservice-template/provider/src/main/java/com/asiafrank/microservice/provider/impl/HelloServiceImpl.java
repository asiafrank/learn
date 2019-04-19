package com.asiafrank.microservice.provider.impl;

import com.asiafrank.microservice.provider.api.HelloService;

import java.text.MessageFormat;

/**
 * HSF 接口实现
 * @author zhangxiaofan 2019/04/18-20:44
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return MessageFormat.format("Hello {0}", name);
    }
}
