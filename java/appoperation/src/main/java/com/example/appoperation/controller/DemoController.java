package com.example.appoperation.controller;

import com.example.appoperation.component.HBaseComponent;
import com.example.appoperation.component.OperationResourceComponent;
import com.example.appoperation.component.OperationResourceWrapper;
import com.example.appoperation.db.base.Page;
import com.example.appoperation.db.base.Pageable;
import com.example.appoperation.db.po.OperationResourcePO;
import com.example.appoperation.db.service.OperationResourceService;
import com.example.appoperation.hbase.ActiveDaysPO;
import com.example.appoperation.redis.UserInfoPO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 查询 HBase，查询 Redis
 * @author zhangxiaofan 2021/01/08-09:53
 */
@RestController
@Slf4j
public class DemoController {

    @Autowired
    private RedisTemplate<String, Object> template;

    @Resource(name="redisTemplate")
    private ValueOperations<String, Object> valueOps;

    @Autowired
    private HBaseComponent hBaseComponent;

    @Autowired
    private OperationResourceComponent operationResourceComponent;

    // userinfo:{userId}
    private final String REDIS_USER_BASE_INFO_PREFIX = "userinfo:";

    /**
     * curl http://127.0.0.1:18092/sync-load?device=iPad&userId=60038515
     */
    @GetMapping("/sync-load")
    public ResponseEntity<ActiveDaysPO> hello(@RequestParam Integer userId,
                                              @RequestParam String device) {
//        valueOps.set(flag, "helloxxxxxx");
//        String v = valueOps.get(flag);
        List<OperationResourceWrapper> resourceList = operationResourceComponent.getResourceList(1);
        ActiveDaysPO po = hBaseComponent.getActiveDays(userId);
        return ResponseEntity.ok(po);
    }

    /**
     * curl http://127.0.0.1:18092/sync-load-list
     */
    @GetMapping("/sync-load-list")
    public ResponseEntity<List<ActiveDaysPO>> list() {
        List<ActiveDaysPO> list = hBaseComponent.findActiveDays();
        return ResponseEntity.ok(list);
    }

    /**
     * curl http://127.0.0.1:18092/del
     */
    @GetMapping("/del")
    public ResponseEntity<Boolean> del() {
        Boolean delete = template.delete("xxxx");
        return ResponseEntity.ok(delete);
    }

    @GetMapping("/build-active-days")
    public ResponseEntity<ActiveDaysPO> buildActiveDays() {
        ActiveDaysPO po = new ActiveDaysPO();
        po.setUserId(63651023);
        po.setActivityDays1(10);
        po.setActivityDays3(30);
        po.setActivityDays7(70);
        po.setActivityDays14(140);
        po.setActivityDays30(300);
        po.setActivityDays60(600);
        hBaseComponent.buildActiveDay(po);
        return ResponseEntity.ok(po);
    }

    @Autowired
    private OperationResourceService operationResourceService;

    /**
     * curl http://127.0.0.1:18092/operation-list
     */
    @GetMapping("/operation-list")
    public ResponseEntity<Page<OperationResourcePO>> pageOperationResourceList() {
        Page<OperationResourcePO> page = operationResourceService.find(Pageable.of(1, 10));
        return ResponseEntity.ok(page);
    }

}
