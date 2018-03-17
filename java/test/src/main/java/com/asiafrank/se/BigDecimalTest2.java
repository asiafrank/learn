package com.asiafrank.se;

import java.math.BigDecimal;

/**
 * Created by Xiaofan Zhang on 6/2/2016.
 */
public class BigDecimalTest2 {
    public static void main(String args[]) {
        System.out.println(moneyVerify(BigDecimal.valueOf(15), 10));
    }

    public static boolean moneyVerify(BigDecimal money, Integer number) {
        // unit = (money - (money*0.006)) / number;
        BigDecimal fee = money.multiply(BigDecimal.valueOf(0.006));
        BigDecimal surplus = money.subtract(fee);
        BigDecimal unit = surplus.divide(BigDecimal.valueOf(number), BigDecimal.ROUND_DOWN);
        int rs = unit.compareTo(BigDecimal.ONE);
        return rs == 1;
    }
}
