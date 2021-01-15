package com.example.appoperation.component;

import com.example.appoperation.hbase.ActiveDaysPO;
import com.example.appoperation.redis.UserInfoPO;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * 分群计算的 component
 *
 * @author zhangxiaofan 2021/01/12-13:26
 */
@Slf4j
public class CalculateComponent {

    /**
     * 活跃天数
     * days: 1昨天  3过去3天，7过去7天，14过去14天，30过去30天，60过去60天
     * min: 最小值
     * max: 最大值, 0 就代表 99999
     * 当天往前推 days 天内活跃了 [min,max] 区间天数
     *
     * activityDays=#activityDays(${days},${min},${max},#para)
     */
    public boolean activeDays(int days, int min, int max, Map<String, Object> param) {
        ActiveDaysPO activeDays = (ActiveDaysPO)param.get("activeDays");
        int d = -1;
        switch (days) {
            case 1: // 昨天
                d = activeDays.getActivityDays1();
                break;
            case 3:
                d = activeDays.getActivityDays3();
                break;
            case 7:
                d = activeDays.getActivityDays7();
                break;
            case 14:
                d = activeDays.getActivityDays14();
                break;
            case 30:
                d = activeDays.getActivityDays30();
                break;
            case 60:
                d = activeDays.getActivityDays60();
                break;
        }
        return d >= min && d <= max;
    }

    /**
     # 用户创建时间, 0表示无限大99999
     # [min,max] 天闭区间
     # min,max Integer
     */
    public boolean createDays(int min, int max, Map<String, Object> param) {
        LocalDateTime now = LocalDateTime.now();
        UserInfoPO user = (UserInfoPO) param.get("user");
        LocalDateTime createTime = user.getCreateTime();

        long until = createTime.until(now, ChronoUnit.DAYS);
        return until >= min && until <= max;
    }

    /**
     # 年龄
     # [min,max] 区间
     # min, max Integer
     */
    public boolean age(int min, int max, Map<String, Object> param) {
        UserInfoPO user = (UserInfoPO)param.get("user");
        Integer age = user.getAge();
        return age >= min && age <= max;
    }
}
