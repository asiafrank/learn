package com.asiafrank.learn.spring.info;

import org.quartz.CronExpression;

import java.text.ParseException;

/**
 * CronInfo
 * <p>
 * Cron util
 * </p>
 * Created at 12/26/2016.
 * http://www.quartz-scheduler.org/documentation/quartz-2.2.x/tutorials/tutorial-lesson-06.html
 * TODO: build cron expression;
 *
 * @author asiafrank
 */
public class CronInfo {
    private String seconds;

    private String minutes;

    private String hours;

    private String dayOfMonth;

    private String month;

    private String dayOfWeek;

    /**
     * Optional
     */
    private String year;

    public boolean verify() {
        String cronExpression = String.format("%1$s %2$s %3$s %4$s %5$s %6$s %7$s",
            seconds, minutes, hours, dayOfMonth, month, dayOfWeek, year);
        try {
            CronExpression ce = new CronExpression(cronExpression);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getSeconds() {
        return seconds;
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
