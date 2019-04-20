package com.asiafrank.microservice.provider.impl;

import com.asiafrank.microservice.provider.api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * HSF 接口实现
 * @author zhangxiaofan 2019/04/18-20:44
 */
public class HelloServiceImpl implements HelloService {

    private static final Logger log = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(String name) {
        log.info("this is log");
        return MessageFormat.format("Hello {0}", name);
    }
}
