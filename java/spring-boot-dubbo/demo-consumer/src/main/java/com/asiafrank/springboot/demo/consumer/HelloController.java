package com.asiafrank.springboot.demo.consumer;

import com.asiafrank.springboot.demo.api.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello
 * @author zhangxiaofan 2021/01/13-10:25
 */
@RestController
public class HelloController {

    @Reference
    private DemoService demoService;

    /**
     * curl http://127.0.0.1:8080/hello?name=somebody
     */
    @GetMapping
    public ResponseEntity<String> hello(@RequestParam String name) {
        String hello = demoService.sayHello(name);
        return ResponseEntity.ok(hello);
    }
}
