package com.example.appoperation.util;

import com.example.appoperation.redis.UserInfoPO;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhangxiaofan 2021/01/15-09:28
 */
public class DeviceTypeUtils {
    // string device to int deviceType
    public static int getDeviceTypeInt(String device) {
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
