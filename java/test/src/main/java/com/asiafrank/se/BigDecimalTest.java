package com.asiafrank.se;

import java.math.BigDecimal;

/**
 * Created by asiafrank on 7/12/2015.
 */
public class BigDecimalTest {
    public static void main(String argv[]) {
        System.out.println("Five ten thousandths(万分之五): " + BigDecimal.valueOf(5, 4));

        BigDecimal b = BigDecimal.valueOf(1000);
        System.out.println(": " + b.divide(BigDecimal.valueOf(10)));

        System.out.println(moneyVerify(b, new Integer(10000)));
        BigDecimal m = BigDecimal.valueOf(1.2);
        System.out.println(m.multiply(BigDecimal.valueOf(2.555)).setScale(2, BigDecimal.ROUND_DOWN).toString());

        BigDecimal unit = b.divide(BigDecimal.valueOf(100), BigDecimal.ROUND_DOWN); // 1000 / 100 = 10
        BigDecimal used = unit.multiply(BigDecimal.valueOf(7)); // 10 * 7 = 70
        BigDecimal surplus = b.subtract(used).setScale(2, BigDecimal.ROUND_DOWN); // 1000 - 70 = 930

        System.out.println(surplus);
    }

    public static boolean moneyVerify(BigDecimal money, Integer number) {
        BigDecimal quotient = money.divide(BigDecimal.valueOf(number));
        int rs = quotient.compareTo(BigDecimal.ONE);
        if (rs == -1) {
            return false;
        }
        return true;
    }
}