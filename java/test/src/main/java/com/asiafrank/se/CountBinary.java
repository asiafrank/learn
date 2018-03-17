package com.asiafrank.se;

public class CountBinary {
    public static void main(String[] args) {
        int num = 16;
        int base;
        int count = 0;
        for (int i = 0;; i++) {
            base = (int)Math.pow(2.0, i);
            if (base > num) break;
            if ((num & base) > 0) {
                count++;
            }
        }
        System.out.println("count: " + count);
    }
}