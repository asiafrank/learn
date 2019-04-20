package com.asiafrank.microservice.provider.impl;

import com.asiafrank.microservice.provider.api.HelloService;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author zhangxiaofan 2019/04/20-09:40
 */
public class HelloServiceImplTest {

    @Test
    public void helloTest() {
        HelloService service = new HelloServiceImpl();
        String result = service.hello("asiafrank");
        Assert.assertEquals("Hello asiafrank", result);
    }
}
