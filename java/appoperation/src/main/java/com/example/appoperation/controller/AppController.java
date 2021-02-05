package com.example.appoperation.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreaker;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.EventObserverRegistry;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.util.TimeUtil;
import com.example.appoperation.component.*;
import com.example.appoperation.db.po.OperationResourcePO;
import com.example.appoperation.db.po.UserClassificationConditionPO;
import com.example.appoperation.hbase.ActiveDaysPO;
import com.example.appoperation.redis.UserInfoPO;
import com.example.appoperation.service.AppOperationService;
import com.example.appoperation.util.DeviceTypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zhangxiaofan 2021/01/15-09:22
 */
@RestController
@RequestMapping("/app")
public class AppController {

    private static final Logger log = LoggerFactory.getLogger(AppController.class);

    /*
    TODO: 客户端请求接口，传递 userId，设备类型，资源位ID
          1. 从 redis 中获取资源位ID 的资源列表 √
          2. 从 redis 中获取 userId 基本信息 √
          3. 从 hbase 中获取 userId 对应的计算信息部分 √
          4. 遍历资源列表，每个资源的用户分群计算，返回 true，false
          5. 只返回前 5 个命中的资源列表

     TODO:性能提升实验
          a.以上1-3步，串行 QPS 实验，并行 QPS 实验。加 guava cache 实验
          b.第 4 步，并发看看提升多少性能
          c.将针对每个 userId 和图片Id，图片表达式计算结果缓存在 local cache 或 redis
            看看提升多少性能。

      TODO: 标签计算进程：其他服务发送标签的 RocketMQ 消息（比如：一个用户购买了课程，发送某个课程的标签）
            接收消息后，将用户群放到 roaringBitMap 中，并且以 标签Id 作为 key，roaringBitMap 序列化为 value
            放入 redis 中（先放入 HBase 里，避免丢失）。
            roaringBitmap 计算写入 HBase 30s写入一次。

      最终版本应该是取消表达式，标签表由 clickhouse 维护，
      直接使用 clickhouse 的 roaringBitmap 来做 or  and 计算即可
      并且 resource 对 userId 的命中结果存入 redis 以及 local cache
     */

    /*
    -server -Xmx1g -Xms1g -XX:+UseG1GC -XX:+UnlockExperimentalVMOptions
    -XX:MaxGCPauseMillis=100 -XX:G1NewSizePercent=8 -XX:InitiatingHeapOccupancyPercent=30
     -XX:MaxTenuringThreshold=1 -XX:G1HeapRegionSize=32m
     -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5
      -XX:GCLogFileSize=20M -XX:+PrintGCDetails -XX:+PrintGCDateStamps
       -XX:+PrintGCCause -Xloggc:gc-%t.log -verbose:gc -XX:+UnlockDiagnosticVMOptions
       -jar kdadmin-main-web.jar --spring.profiles.active=dev --java.net.preferIPv4Stack=true
     */

    @Autowired
    private AppOperationService appOperationService;

    /**
     * 多图
     * curl "http://127.0.0.1:18092/app/multiImg?resourceLocationId=1&device=iPad&userId=88888888"
     *
     * 压测：https://github.com/wg/wrk/blob/master/README.md
     * ./wrk -t12 -c400 -d30s "http://127.0.0.1:18092/app/multiImg?resourceLocationId=1&device=iPad&userId=88888888"
     *
     * 无优化，压测结果. JVM:
     * -server -Xmx2G -Xms2G -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCCause -verbose:gc
     * 12 threads and 400 connections
     *   Thread Stats   Avg      Stdev     Max   +/- Stdev
     *     Latency   217.62ms   95.88ms 918.02ms   83.17%
     *     Req/Sec   155.52     39.32   270.00     74.51%
     *   55465 requests in 30.10s, 230.26MB read
     *   Socket errors: connect 0, read 689, write 0, timeout 0
     * Requests/sec:   1842.64
     * Transfer/sec:      7.65MB
     *
     * 线程池并发优化，压测结果：
     * 40个线程，qps 2657.13
     * Running 30s test @ http://127.0.0.1:18092/app/multiImg?resourceLocationId=1&device=iPad&userId=88888888
     *   12 threads and 400 connections
     *   Thread Stats   Avg      Stdev     Max   +/- Stdev
     *     Latency   148.33ms   19.52ms 256.39ms   79.34%
     *     Req/Sec   222.54     39.36   323.00     68.02%
     *   79975 requests in 30.10s, 332.02MB read
     *   Socket errors: connect 0, read 511, write 0, timeout 0
     * Requests/sec:   2657.13
     * Transfer/sec:     11.03MB
     *
     * 30个线程，qps 2553
     * Running 30s test @ http://127.0.0.1:18092/app/multiImg?resourceLocationId=1&device=iPad&userId=88888888
     *   12 threads and 400 connections
     *   Thread Stats   Avg      Stdev     Max   +/- Stdev
     *     Latency   154.33ms   27.84ms 333.86ms   85.91%
     *     Req/Sec   214.48     47.95   333.00     71.13%
     *   76862 requests in 30.10s, 319.10MB read
     *   Socket errors: connect 0, read 526, write 0, timeout 0
     * Requests/sec:   2553.63
     * Transfer/sec:     10.60MB
     *
     * 20个线程，qps 2295
     * Running 30s test @ http://127.0.0.1:18092/app/multiImg?resourceLocationId=1&device=iPad&userId=88888888
     *   12 threads and 400 connections
     *   Thread Stats   Avg      Stdev     Max   +/- Stdev
     *     Latency   171.51ms   31.05ms 306.97ms   73.86%
     *     Req/Sec   192.30     45.76   320.00     66.81%
     *   69025 requests in 30.07s, 286.55MB read
     *   Socket errors: connect 0, read 536, write 0, timeout 0
     * Requests/sec:   2295.40
     * Transfer/sec:      9.53MB
     *
     * 结果，线程池并发优化效果明显.
     */
    @GetMapping("/multiImg")
    public ResponseEntity<List<OperationResourcePO>> multiImg(
            @RequestParam Integer resourceLocationId,
            @RequestParam Integer userId,
            @RequestParam String device) throws ExecutionException, InterruptedException {
        List<OperationResourcePO> list = appOperationService.multiImg(resourceLocationId, userId, device);
        return ResponseEntity.ok(list);
    }
}
