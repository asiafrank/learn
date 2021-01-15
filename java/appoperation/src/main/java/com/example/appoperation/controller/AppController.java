package com.example.appoperation.controller;

import com.example.appoperation.component.*;
import com.example.appoperation.db.po.OperationResourcePO;
import com.example.appoperation.db.po.UserClassificationConditionPO;
import com.example.appoperation.hbase.ActiveDaysPO;
import com.example.appoperation.redis.UserInfoPO;
import com.example.appoperation.util.DeviceTypeUtils;
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

import java.util.*;

/**
 * @author zhangxiaofan 2021/01/15-09:22
 */
@RestController
@RequestMapping("/app")
public class AppController {

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

      TODO: 其他服务发送标签的 RocketMQ 消息（比如：一个用户购买了课程，发送某个课程的标签）
            接收消息后，将用户群放到 roaringBitMap 中，并且以 标签Id 作为 key，roaringBitMap 序列化为 value
            放入 redis 中（先放入 HBase 里，避免丢失）。
     */

    @Autowired
    private HBaseComponent hBaseComponent;

    @Autowired
    private OperationResourceComponent operationResourceComponent;

    @Autowired
    private UserComponent userComponent;

    private final CalculateComponent calculateComponent = new CalculateComponent();

    /*
    -server -Xmx1g -Xms1g -XX:+UseG1GC -XX:+UnlockExperimentalVMOptions
    -XX:MaxGCPauseMillis=100 -XX:G1NewSizePercent=8 -XX:InitiatingHeapOccupancyPercent=30
     -XX:MaxTenuringThreshold=1 -XX:G1HeapRegionSize=32m
     -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5
      -XX:GCLogFileSize=20M -XX:+PrintGCDetails -XX:+PrintGCDateStamps
       -XX:+PrintGCCause -Xloggc:gc-%t.log -verbose:gc -XX:+UnlockDiagnosticVMOptions
       -jar kdadmin-main-web.jar --spring.profiles.active=dev --java.net.preferIPv4Stack=true
     */

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
     */
    @GetMapping("/multiImg")
    public ResponseEntity<List<OperationResourcePO>> multiImg(
            @RequestParam Integer resourceLocationId,
            @RequestParam Integer userId,
            @RequestParam String device) {
        UserInfoPO user = userComponent.getUserById(userId);
        user.setDeviceType(DeviceTypeUtils.getDeviceTypeInt(device));

        ActiveDaysPO activeDays = hBaseComponent.getActiveDays(userId);

        Map<String, Object> param = new HashMap<>();
        param.put("user", user);
        param.put("activeDays", activeDays);

        List<OperationResourceWrapper> resourceList = operationResourceComponent.getResourceList(resourceLocationId);

        List<OperationResourcePO> resultList = new ArrayList<>(); // 该用户命中的资源 list

        ExpressionParser expressionParser = new SpelExpressionParser();
        EvaluationContext ctx = new StandardEvaluationContext(calculateComponent);
        ctx.setVariable("param", param);
        for (OperationResourceWrapper w : resourceList) {
            OperationResourcePO resourcePO = w.getResourcePO();
            List<UserClassificationConditionPO> ruleList = w.getRuleList();
            if (Objects.isNull(ruleList)) { // 没有 rule 对象也算命中
                resultList.add(resourcePO);
            }

            for (UserClassificationConditionPO rule : ruleList) {
                Expression expr = expressionParser.parseExpression(rule.getFullExpression());
                Boolean r = (Boolean) expr.getValue(ctx);

                if (r == Boolean.TRUE) { // 只要命中一个规则，就算 resourcePO 命中
                    resultList.add(resourcePO);
                    break;
                }
            }
        }
        return ResponseEntity.ok(resultList);
    }
}
