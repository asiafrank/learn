package com.asiafrank.se;

import java.util.*;

/**
 * Created by asiafrank on 7/12/2015.
 */
public class CalendarTest {
    public static void main(String argv[]) {
        Calendar c = Calendar.getInstance();
        // current time
        System.out.println("time: " + c.getTime());

        // start of today
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        System.out.println("time: " + c.getTime());

        // specific day
        c.set(Calendar.DAY_OF_MONTH, 0);
        System.out.println("time: " + c.getTime());

        Date createdAt = new Date();
        c.setTime(createdAt);
        c.add(Calendar.DATE, 4);
        System.out.println("time: " + c.getTime());
    }
}
