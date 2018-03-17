package com.asiafrank.se;

import java.math.BigDecimal;

/**
 * Created by Xiaofan Zhang on 9/3/2016.
 */
public class BigDecimalTest4 {
    public static void main(String[] args) {
        // create 2 BigDecimal Objects
        BigDecimal bg1, bg2;

        bg1 = new BigDecimal("123.234");
        bg2 = new BigDecimal("523");

        // create 2 int objects
        int i1, i2;

        // assign the result of precision of bg1, bg2 to i1 and i2
        i1 = bg1.precision();
        i2 = bg2.precision();

        String str1 = "The precision of " + bg1 + " is " + i1;
        String str2 = "The precision of " + bg2 + " is " + i2;

        // print the values of i1, i2
        System.out.println( str1 );
        System.out.println( str2 );
    }
}
