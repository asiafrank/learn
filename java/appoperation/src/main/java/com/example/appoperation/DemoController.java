package com.example.appoperation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 查询 HBase，查询 Redis
 * @author zhangxiaofan 2021/01/08-09:53
 */
@RestController
@Slf4j
public class DemoController {

    @Autowired
    private RedisTemplate<String, String> template;

    @Resource(name="redisTemplate")
    private ValueOperations<String, String> valueOps;

    private String flag = "namexyz";

    /*
    TODO: 客户端请求接口，传递 userId，设备类型，资源位ID
          1. 从 redis 中获取资源位ID 的资源列表
          2. 从 redis 中获取 userId 基本信息
          3. 从 hbase 中获取 userId 对应的计算信息部分
          4. 遍历资源列表，每个资源的用户分群计算，返回 true，false
          5. 只返回前 5 个命中的资源列表
          以上3步，串行 QPS 实验，并行 QPS 实验。加 guava cache 实验
     */

    /**
     * curl http://127.0.0.1:18092/hello
     */
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        valueOps.set(flag, "helloxxxxxx");
        String v = valueOps.get(flag);
        return ResponseEntity.ok(v);
    }

    /**
     * curl http://127.0.0.1:18092/del
     */
    @GetMapping("/del")
    public ResponseEntity<Boolean> del() {
        Boolean delete = template.delete(flag);
        return ResponseEntity.ok(delete);
    }
}
