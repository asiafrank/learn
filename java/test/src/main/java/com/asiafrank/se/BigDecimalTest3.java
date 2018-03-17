package com.asiafrank.se;

import java.math.BigDecimal;

/**
 * Created by Xiaofan Zhang on 6/2/2016.
 */
public class BigDecimalTest3 {
    public static void main(String args[]) {
        BigDecimal value = BigDecimal.valueOf(3.311);
        System.out.println(value.setScale(2, BigDecimal.ROUND_CEILING));
    }
}