package com.asiafrank.se;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Xiaofan Zhang on 4/3/2016.
 */
public class DateTest {
    public static void main(String[] args) {
        System.out.println(new Date().getTime());
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date bTimeDate = format.parse("2016-04-18 23:34:32");
            System.out.println(bTimeDate);
            System.out.println(bTimeDate.getYear());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}