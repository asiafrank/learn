package com.example.appoperation.util;

import java.time.*;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static final int FIRST_DAY_OF_WEEK = Calendar.MONDAY; // 中国周一是一周的第一天
    /**
     * 根据日期取得对应周周一日期
     *
     * @param date
     * @param startFlag  true 返回00：00：00 000 ，false 默认
     *
     * @return
     */
    public static Date getMondayOfWeek(Date date, boolean startFlag) {
        Calendar monday = Calendar.getInstance();
        monday.setTime(date);
        monday.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
        monday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        if(startFlag){
            monday.set(Calendar.HOUR_OF_DAY, 0);
            monday.set(Calendar.MINUTE, 0);
            monday.set(Calendar.SECOND, 0);
            monday.set(Calendar.MILLISECOND, 0);
        }
        return monday.getTime();
    }

    /**
     * 根据日期取得对应周周日日期
     *
     * @param date
     * @param endFlag true 返回23：59：59：999毫秒 false 默认
     *
     * @return
     */
    public static Date getSundayOfWeek(Date date, boolean endFlag) {
        Calendar sunday = Calendar.getInstance();
        sunday.setTime(date);
        sunday.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
        sunday.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        if(endFlag){
            sunday.set(Calendar.HOUR_OF_DAY, 23);
            sunday.set(Calendar.MINUTE, 59);
            sunday.set(Calendar.SECOND, 59);
            sunday.set(Calendar.MILLISECOND, 999);
        }
        return sunday.getTime();
    }

    /**
     * 默认时区均使用 "Asia/Shanghai"
     */
    public static final ZoneId DEFAULT_ZONE = ZoneId.of("Asia/Shanghai");

    /**
     * <p>通过 date 计算一周的<b>开始</b>时间。
     * <p>即：getMondayOfWeek 返回 Long 的版本，并且 startFlag 为 true。
     *
     * <p>比如：当传入参数 date 为 "2019-06-11 14:36:23" 时，返回 1560096000000
     * <p>即 "Mon Jun 10 00:00:00 CST 2019" 的 Date
     *
     * <p>2019 年 6 月日历如下
     * <pre>
     *       六月 2019
     * 一  二 三 四  五 六 日
     *                 1  2
     *  3  4  5  6  7  8  9
     * 10 11 12 13 14 15 16
     * 17 18 19 20 21 22 23
     * 24 25 26 27 28 29 30
     * </pre>
     *
     * @revise zhangxiaofan
     */
    public static long getStartOfWeekLong(Date date) {
        return getMondayOfWeek(date, true).getTime();
    }

    /**
     * {@link #getStartOfWeekLong(Date)} 的 LocalDateTime 参数版本
     * @param t LocalDateTime 参数（java-1.8 的时间类很方便加减）
     * @return startOfWeekLong
     */
    public static Long getStartOfWeekLong(LocalDateTime t) {
        assert t != null;
        Date date = Date.from(t.atZone(DEFAULT_ZONE).toInstant());
        return getStartOfWeekLong(date);
    }

    /**
     * <p>通过 date 计算一周的<b>结束</b>时间。
     * <p>即：getSundayOfWeek 返回 Long 的版本，并且 endFlag 为 true。
     *
     * <p>比如：当传入参数 date 为 "2019-06-11 14:36:23" 时，返回 1560700799999
     * <p>即 "Sun Jun 16 23:59:59 CST 2019" 的 Date
     *
     * <p>2019 年 6 月日历如下
     * <pre>
     *       六月 2019
     * 一  二 三 四  五 六 日
     *                 1  2
     *  3  4  5  6  7  8  9
     * 10 11 12 13 14 15 16
     * 17 18 19 20 21 22 23
     * 24 25 26 27 28 29 30
     * </pre>
     *
     * @revise zhangxiaofan
     */
    public static Long getEndOfWeekLong(Date date) {
        return getSundayOfWeek(date, true).getTime();
    }

    /**
     * {@link #getEndOfWeekLong(Date)} 的 LocalDateTime 参数版本
     * @param t LocalDateTime 参数（java-1.8 的时间类很方便加减）
     * @return endOfWeekLong
     */
    public static Long getEndOfWeekLong(LocalDateTime t) {
        assert t != null;
        Date date = Date.from(t.atZone(DEFAULT_ZONE).toInstant());
        return getEndOfWeekLong(date);
    }

    //----------------------------------------------------
    // 传统的 Date 和 LocalDateTime 互转
    // 参考：https://howtodoinjava.com/java/date-time/localdatetime-to-date/
    //----------------------------------------------------

    /**
     * <p>localDate 转成 Date
     *
     * @param  d LocalDate型的时间
     * @return Date
     */
    public static Date asDate(LocalDate d) {
        return Date.from(d.atStartOfDay()
                          .atZone(DEFAULT_ZONE)
                          .toInstant());
    }

    /**
     * <p>localDateTime 转成 Date
     *
     * @param d LocalDateTime 型的时间
     * @return Date
     */
    public static Date asDate(LocalDateTime d) {
        return Date.from(d.atZone(DEFAULT_ZONE)
                          .toInstant());
    }

    /**
     * <p>date 转成 LocalDate
     *
     * @param d Date 型的时间
     * @return LocalDate
     */
    public static LocalDate asLocalDate(Date d) {
        return Instant.ofEpochMilli(d.getTime())
                      .atZone(DEFAULT_ZONE)
                      .toLocalDate();
    }

    /**
     * <p>date 转成 LocalDateTime
     *
     * @param d Date 型的时间
     * @return LocalDateTime
     */
    public static LocalDateTime asLocalDateTime(Date d) {
        return Instant.ofEpochMilli(d.getTime())
                      .atZone(DEFAULT_ZONE)
                      .toLocalDateTime();
    }

    /**
     * <p>long 转成 LocalDateTime
     *
     * @param l long 型的时间
     * @return LocalDateTime
     */
    public static LocalDateTime asLocalDateTime(long l) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(l), DEFAULT_ZONE);
    }

    /**
     * <p>LocalDateTime 转成 long
     *
     * @param d LocalDateTime 型的时间
     * @return long 毫秒
     */
    public static long asLong(LocalDateTime d) {
        ZonedDateTime zdt = d.atZone(DEFAULT_ZONE);
        return zdt.toInstant().toEpochMilli();
    }

    /**
     * 裁剪时间，抓换为 LocalDateTime。
     * 如：2020-03-26T04:00:00.000Z 裁剪为 2020-03-26T04:00:00 然后再转换
     * 避免报错 "java.time.format.DateTimeParseException: Text '2020-03-26T04:00:00.000Z' could not be parsed, unparsed text found at index 23"
     * @param dateStr
     * @return
     */
    public static LocalDateTime cutAsLocalDateTime(String dateStr) {
        if (dateStr.length() > 19) {
            dateStr = dateStr.substring(0, 19);
        }
        return LocalDateTime.parse(dateStr);
    }
}