package com.asiafrank.se.test;

public class Employee {
    public static void main(String[] args) {
        int x = 3;
        System.out.println(count(x));
    }

    private static int count(int level) {
        if (level < 0 || level > 10) {
            throw new IllegalArgumentException("Wrong number");
        }

        if (level == 0) {
            return 1;
        }

        int e = 1;
        for (int i = 0; i < level; i++) {
            e *= 7;
        }
        return e + count(--level);
    }
}
