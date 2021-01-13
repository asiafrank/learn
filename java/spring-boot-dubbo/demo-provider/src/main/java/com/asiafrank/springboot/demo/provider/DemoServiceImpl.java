package com.asiafrank.springboot.demo.provider;

import com.asiafrank.springboot.demo.api.DemoService;
import org.apache.dubbo.config.annotation.Service;

/**
 * 实现类
 * @author zhangxiaofan 2021/01/13-10:40
 */
@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "hello " + name + "!";
    }
}
