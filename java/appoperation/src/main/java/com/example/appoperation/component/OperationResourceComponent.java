package com.example.appoperation.component;

import com.example.appoperation.db.base.OrderBy;
import com.example.appoperation.db.po.*;
import com.example.appoperation.db.query.OperationResourceWeeklySortingQuery;
import com.example.appoperation.db.service.OperationResourceService;
import com.example.appoperation.db.service.OperationResourceWeeklySortingService;
import com.example.appoperation.db.service.UserClassificationConditionService;
import com.example.appoperation.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * @author zhangxiaofan 2021/01/14-15:13
 */
@Component
public class OperationResourceComponent {
    /**
     * 用户分群运营-资源位缓存
     * OPER_RES_LOCATION_{resourceLocationId}
     * type string
     * value = list of {@link OperationResourceLocationPO}
     */
    public static final String OPER_RES_LOCATION_PREFIX = "OPER_RES_LOCATION_";

    /**
     * 用户分群运营-资源缓存(对应资源位的)
     * OPER_RES_{resourceLocationId}
     * type string
     * value = list of {@link OperationResourcePO}
     */
    public static final String OPER_RES_PREFIX = "OPER_RES_";

    /**
     * 用户分群运营-资源周排序缓存 √
     * OPER_RES_WEEK_SORT_{resourceLocationId}_{weekBegin}
     * type string
     * value = list of {@link OperationResourcePO}
     */
    public static final String OPER_RES_WEEK_SORT_PREFIX = "OPER_RES_WEEK_SORT_";

    /**
     * 用户分群运营-资源用户条件缓存(对应资源的条件列表) √
     * OPER_RES_USER_CONDITION_{operationResourceId}
     * type string
     * value = list of {@link UserClassificationConditionPO}
     */
    public static final String OPER_RES_USER_CONDITION_PREFIX = "OPER_RES_USER_CONDITION_";

    @Autowired
    private RedisTemplate<String, Object> template;

    @Resource(name="redisTemplate")
    private ValueOperations<String, List<OperationResourcePO>> resourceListOps;
    @Resource(name="redisTemplate")
    private ValueOperations<String, List<OperationResourceWeeklySortingPO>> resourceWeeklySortingListOps;
    @Resource(name="redisTemplate")
    private ValueOperations<String, List<UserClassificationConditionPO>> ruleListOps;

    @Autowired
    private OperationResourceService operationResourceService;

    @Autowired
    private OperationResourceWeeklySortingService operationResourceWeeklySortingService;

    @Autowired
    private UserClassificationConditionService userClassificationConditionService;

    /**
     * 获取周排序资源列表
     *
     * 1.资源周排序中资源列表
     * 2.获取资源中的用户分群列表
     * 3.拼装成 Wrapper list
     *
     * @param locationId {@link OperationResourceLocationPO#getId()}
     * @return 资源列表以及用户分群列表
     */
    public List<OperationResourceWrapper> getResourceList(Integer locationId) {
        LocalTime dayStartTime = LocalTime.of(0, 0, 0, 0);
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), dayStartTime);
        long todayLong = DateUtils.asLong(today);

        List<OperationResourcePO> resourceList = getResourceListByWeeklySort(locationId, todayLong);
        List<Integer> resourceIds = new ArrayList<>(resourceList.size());
        for (OperationResourcePO po : resourceList) {
            resourceIds.add(po.getId());
        }
        Map<Integer, List<UserClassificationConditionPO>> userClassificationMap = getUserClassificationMap(resourceIds);

        List<OperationResourceWrapper> wrappers = new ArrayList<>(resourceList.size());
        for (OperationResourcePO po : resourceList) {
            Integer resourceId = po.getId();
            OperationResourceWrapper w = new OperationResourceWrapper();
            w.setResourcePO(po);
            List<UserClassificationConditionPO> rules = userClassificationMap.get(resourceId);
            w.setRulePO(rules);
            wrappers.add(w);
        }
        return wrappers;
    }

    private Map<Integer, List<UserClassificationConditionPO>> getUserClassificationMap(List<Integer> resourceIds) {
        Map<Integer, List<UserClassificationConditionPO>> map = new HashMap<>();
        for (Integer resourceId : resourceIds) {
            String key = OPER_RES_USER_CONDITION_PREFIX + resourceId;
            List<UserClassificationConditionPO> rules = ruleListOps.get(key);
            if (Objects.isNull(rules)) {
                rules = userClassificationConditionService.findUserClassificationsByResourceId(resourceId);
                ruleListOps.set(key, rules);
            }
            map.put(resourceId, rules);
        }
        return map;
    }

    private List<OperationResourcePO> getResourceListByWeeklySort(Integer locationId, Long todayBeginLong) {
        String key = OPER_RES_WEEK_SORT_PREFIX + locationId + "_" + todayBeginLong;
        List<OperationResourcePO> list = resourceListOps.get(key);
        if (Objects.isNull(list)) { // 从数据库里获取，并且塞入缓存
            list = operationResourceWeeklySortingService.findResource(locationId, todayBeginLong);
            resourceListOps.set(key, list);
        }
        return list;
    }
}
