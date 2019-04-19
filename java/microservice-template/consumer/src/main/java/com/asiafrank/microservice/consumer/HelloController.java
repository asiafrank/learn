package com.asiafrank.microservice.consumer;

import com.asiafrank.microservice.provider.api.HelloService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * spring boot 示例
 *
 * @author zhangxiaofan 2019/04/17-19:21
 */
@Controller
@RequestMapping(value = "/hello")
public class HelloController {

    @Autowired
    private HelloService helloService;

    @ApiOperation(value = "Hello World")
    @GetMapping
    @ResponseBody
    public String hello(String name) {
        return helloService.hello(name);
    }
}
