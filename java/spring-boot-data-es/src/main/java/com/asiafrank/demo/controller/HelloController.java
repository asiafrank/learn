package com.asiafrank.demo.controller;

import com.asiafrank.demo.core.bo.SampleBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * hello
 * @author zhangxiaofan 2020/07/09-09:54
 */
@RestController
@RequestMapping("/hello")
public class HelloController {
    // TODO: ES 同步程序实验
    // TODO: queryTimeout kill 实验, 怎么不起作用?

    @Autowired
    private SampleBO sampleBO;

    @GetMapping
    public ResponseEntity<String> hello() {
        sampleBO.take();
        return ResponseEntity.ok("hello");
    }
}
