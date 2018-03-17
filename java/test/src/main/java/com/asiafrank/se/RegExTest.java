package com.asiafrank.se;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by asiafrank on 1/2/2016.
 */
public class RegExTest {
    public static void main(String[] args) {
        // Create a Pattern object
//        Pattern r = Pattern.compile("(?!^[0-9]+$)(?!^[A-z]+$)(?!^[^A-z0-9]+$)^.{6,16}$");
        Pattern r = Pattern.compile("(?!^[0-9]+$)(?!^[A-z]+$)(?!^[^A-z0-9]+$)^.*$");

        // Now create matcher object.
        Matcher m = r.matcher("##ddf324");
        if (m.find()) {
            System.out.println("Found value: " + m.group(0));
//            System.out.println("Found value: " + m.group(1));
//            System.out.println("Found value: " + m.group(2));
        }

        Pattern p2 = Pattern.compile("\\d+");
        Matcher m2 = p2.matcher("12");
        System.out.println(m2.matches());

        Pattern p3 = Pattern.compile("^.{6,12}$");
        Matcher m3 = p3.matcher("1234567890123");
        System.out.println("p3: " + m3.matches());

        Pattern p4 = Pattern.compile("(.+)?kksdf(.+)?");
        Matcher m4 = p4.matcher("kksdf");
        System.out.println("p4: " + m4.matches());
    }
}
