package com.asiafrank.springboot.demo.provider;

import com.asiafrank.springboot.demo.api.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

/**
 * 实现类
 * @author zhangxiaofan 2021/01/13-10:40
 */
@DubboService
@Slf4j
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        Object tag = RpcContext.getContext().get("tag");
        log.info("impl tag: {}", tag);
        return "hello " + name + "!";
    }
}
