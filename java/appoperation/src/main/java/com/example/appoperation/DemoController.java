package com.example.appoperation;

import com.example.appoperation.component.OperationResourceComponent;
import com.example.appoperation.component.OperationResourceWrapper;
import com.example.appoperation.db.base.Page;
import com.example.appoperation.db.base.Pageable;
import com.example.appoperation.db.po.OperationResourcePO;
import com.example.appoperation.db.service.OperationResourceService;
import com.example.appoperation.hbase.ActiveDaysPO;
import com.example.appoperation.redis.UserInfoPO;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
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

    /*
    TODO: 客户端请求接口，传递 userId，设备类型，资源位ID
          1. 从 redis 中获取资源位ID 的资源列表
          2. 从 redis 中获取 userId 基本信息
          3. 从 hbase 中获取 userId 对应的计算信息部分
          4. 遍历资源列表，每个资源的用户分群计算，返回 true，false
          5. 只返回前 5 个命中的资源列表

     TODO:性能提升实验
          a.以上1-3步，串行 QPS 实验，并行 QPS 实验。加 guava cache 实验
          b.第 4 步，并发看看提升多少性能
          c.将针对每个 userId 和图片Id，图片表达式计算结果缓存在 local cache 或 redis
            看看提升多少性能。
     */

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

    //---------- 辅助代码 ----------------------

    // string device to int deviceType
    private static int getDeviceTypeInt(String device) {
        if (StringUtils.isBlank(device)) {
            return UserInfoPO.DeviceType.Android;
        }
        String d = device.toLowerCase();
        switch (d) {
            case "ipad":
                return UserInfoPO.DeviceType.iPad;
            case "iphone":
                return UserInfoPO.DeviceType.iPhone;
            case "ipod":
                return UserInfoPO.DeviceType.iPod;
            case "androidpad":
                return UserInfoPO.DeviceType.AndroidPad;
            case "androidwatch":
                return UserInfoPO.DeviceType.AndroidWatch;
            case "x86":
                return UserInfoPO.DeviceType.x86;
            default: // 是默认 Android
                return UserInfoPO.DeviceType.Android;
        }
    }

}
