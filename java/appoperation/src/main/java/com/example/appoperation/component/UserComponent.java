package com.example.appoperation.component;

import com.example.appoperation.redis.UserInfoPO;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 获取用户基本信息
 * @author zhangxiaofan 2021/01/15-09:30
 */
@Component
public class UserComponent {

    // user_info:{userId}
    private static final String USER_INFO_PREFIX = "user_info:";

    @Resource(name="redisTemplate")
    private ValueOperations<String, UserInfoPO> userInfoOps;

    public UserInfoPO getUserById(Integer userId) {
        String key = USER_INFO_PREFIX + userId;
        UserInfoPO po = userInfoOps.get(key);
        if (Objects.isNull(po)) { // 假装从 MySQL 获取
            po = new UserInfoPO();
            po.setUserId(userId);
            po.setAge(5);
            po.setGender(1);
            po.setDeviceType(UserInfoPO.DeviceType.iPhone);
            po.setCreateTime(LocalDateTime.of(2020, 1, 10, 12, 12)); // 2020-01-10T12:12
            po.setUpdateTime(LocalDateTime.of(2020, 1, 10, 12, 12)); // 2020-01-10T12:12
            userInfoOps.set(key, po);
        }
        return po;
    }

}
