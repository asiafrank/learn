package com.example.appoperation.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
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
import com.example.appoperation.util.DeviceTypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zhangxiaofan 2021/02/05-10:29
 */
@Component
public class AppOperationService {
    private static final Logger log = LoggerFactory.getLogger(AppOperationService.class);

    @Autowired
    private HBaseComponent hBaseComponent;

    @Autowired
    private OperationResourceComponent operationResourceComponent;

    @Autowired
    private UserComponent userComponent;

    @Autowired
    private ThreadPoolExecutor exec;

    private final CalculateComponent calculateComponent = new CalculateComponent();

    /**
     * sentinel 限流降级：https://github.com/alibaba/Sentinel/wiki/%E5%A6%82%E4%BD%95%E4%BD%BF%E7%94%A8
     */
    @PostConstruct
    public void init() {
        // 资源流控
        List<FlowRule> rules = new ArrayList<>();
        FlowRule r = new FlowRule();
        r.setResource("multiImg");
        r.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        r.setCount(3000);
        rules.add(r);
        FlowRuleManager.loadRules(rules);

        // 熔断时触发
        EventObserverRegistry.getInstance().addStateChangeObserver("logging",
                (prevState, newState, rule, snapshotValue) -> {
                    if (newState == CircuitBreaker.State.OPEN) {
                        // 变换至 OPEN state 时会携带触发时的值
                        System.err.printf("%s -> OPEN at %d, snapshotValue=%.2f%n", prevState.name(),
                                TimeUtil.currentTimeMillis(), snapshotValue);
                    } else {
                        System.err.printf("%s -> %s at %d%n", prevState.name(), newState.name(),
                                TimeUtil.currentTimeMillis());
                    }
                });
    }

    // 限流和降级
    @SentinelResource(value = "multiImg", blockHandler = "blockHandler", fallback = "fallback")
    public List<OperationResourcePO> multiImg(
            Integer resourceLocationId,
            Integer userId,
            String device) throws ExecutionException, InterruptedException {
        // 并行获取 userInfoPO, activeDays, resourceList
        Future<UserInfoPO> userInfoFuture = exec.submit(() -> userComponent.getUserById(userId));
        Future<ActiveDaysPO> activeDaysFuture = exec.submit(() -> hBaseComponent.getActiveDays(userId));
        Future<List<OperationResourceWrapper>> resourceListFuture = exec.submit(() -> operationResourceComponent.getResourceList(resourceLocationId));

        UserInfoPO user = userInfoFuture.get();
        user.setDeviceType(DeviceTypeUtils.getDeviceTypeInt(device));

        ActiveDaysPO activeDays = activeDaysFuture.get();

        Map<String, Object> param = new HashMap<>();
        param.put("user", user);
        param.put("activeDays", activeDays);

        List<OperationResourceWrapper> resourceList = resourceListFuture.get();
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
        return resultList;
    }

    public List<OperationResourcePO> blockHandler(Integer resourceLocationId,
                                                  Integer userId,
                                                  String device, Throwable ex)
    {
        log.error("blockHandler......", ex);

        OperationResourcePO dummy = new OperationResourcePO();
        dummy.setTitle("报错降级资源");
        List<OperationResourcePO> resultList = new ArrayList<>(1);
        resultList.add(dummy);
        return resultList;
    }

    // 降级方法，参数要与原方法一致
    public List<OperationResourcePO> fallback(Integer resourceLocationId,
                                              Integer userId,
                                              String device)
    {
        log.error("fallback......");

        OperationResourcePO dummy = new OperationResourcePO();
        dummy.setTitle("降级资源");
        List<OperationResourcePO> resultList = new ArrayList<>(1);
        resultList.add(dummy);
        return resultList;
    }
}
