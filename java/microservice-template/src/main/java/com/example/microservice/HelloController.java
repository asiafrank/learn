package com.example.microservice;

import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "Hello World")
    @GetMapping
    @ResponseBody
    public String hello() {
        return "hello";
    }
}
