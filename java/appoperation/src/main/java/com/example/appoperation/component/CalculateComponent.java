package com.example.appoperation.component;

import lombok.extern.slf4j.Slf4j;

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
    public boolean activeDays(int num, String s, Map<String, Object> param) {
        log.info("activeDays invoke......num={}, s={}, param={}", num, s, param);
        return false;
    }

    /**
     # 用户创建时间, 0表示无限大99999
     # [min,max] 天闭区间
     # min,max Integer
     */
    public boolean createDays(int min, int max, Map<String, Object> param) {
        // TODO:
        return false;
    }

    /**
     # 年龄
     # [min,max] 区间
     # min, max Integer
     */
    public boolean age(int min, int max, Map<String, Object> param) {
        // TODO:
        return false;
    }
}
