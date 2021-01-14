package com.example.appoperation;

import com.example.appoperation.util.DateUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * @author zhangxiaofan 2021/01/14-15:33
 */
public class DateTest {
    @Test
    @Ignore
    public void test() {
        long t = 1610553600000L; // 2021-01-14T00:00
        LocalDateTime dateTime = DateUtils.asLocalDateTime(t);
        System.out.println(dateTime);
    }
}
