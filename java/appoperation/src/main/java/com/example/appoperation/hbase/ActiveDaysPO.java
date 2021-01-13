package com.example.appoperation.hbase;

import lombok.Data;

/**
 * 用户活跃天数
 * # days: 1昨天  3过去3天，7过去7天，14过去14天，30过去30天，60过去60天
 * # min: 最小值
 * # max: 最大值, 0 就代表 99999
 * # 当天往前推 days 天内活跃了 [min,max] 区间天数
 * @author zhangxiaofan 2021/01/11-10:40
 */
@Data
public class ActiveDaysPO {
    private Integer userId;
    private Integer activityDays1;
    private Integer activityDays3;
    private Integer activityDays7;
    private Integer activityDays14;
    private Integer activityDays30;
    private Integer activityDays60;
    private String dataCreationDate;

    public ActiveDaysPO() { // 默认 0
        this.activityDays1 = 0;
        this.activityDays3 = 0;
        this.activityDays7 = 0;
        this.activityDays14 = 0;
        this.activityDays30 = 0;
        this.activityDays60 = 0;
    }
}
