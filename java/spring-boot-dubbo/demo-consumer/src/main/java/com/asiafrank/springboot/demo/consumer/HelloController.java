package com.asiafrank.springboot.demo.consumer;

import com.asiafrank.springboot.demo.api.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello
 * @author zhangxiaofan 2021/01/13-10:25
 */
@RestController
@Slf4j
public class HelloController {

    @DubboReference
    private DemoService demoService;

    /**
     * curl http://127.0.0.1:8080/hello?name=somebody
     */
    @GetMapping
    public ResponseEntity<String> hello(@RequestParam String name) {
        Object tag = RpcContext.getContext().get("tag");
        log.info("controller tag: {}", tag);
        String hello = demoService.sayHello(name);
        return ResponseEntity.ok(hello);
    }
}
