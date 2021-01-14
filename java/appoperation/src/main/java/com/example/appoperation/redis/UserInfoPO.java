package com.example.appoperation.redis;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户信息
 * @author zhangxiaofan 2021/01/14-11:05
 */
@Data
public class UserInfoPO {
    private Integer userId;
    /**
     * 1男，2女，3未知
     */
    private Integer gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 设备类型  ipad:1  iphone:2  ipod:3  android:4(是默认)  androidPad: 5  x86:0
     */
    private Integer deviceType;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public static class Gender {
        public static final int BOY = 1;
        public static final int GIRL = 2;
        public static final int UNKNOWN = 3;
    }

    public static class DeviceType {
        public static final int iPad = 1;
        public static final int iPhone = 2;
        public static final int iPod = 3;
        public static final int Android = 4;
        public static final int AndroidPad = 5;
        public static final int AndroidWatch = 6;
        public static final int x86 = 0;
    }
}
