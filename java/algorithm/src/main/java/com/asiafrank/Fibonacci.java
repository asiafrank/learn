package com.asiafrank;

/**
 * 斐波那契数列
 * @author zhangxf created at 1/18/2019.
 */
public class Fibonacci {
    public static void main(String[] args) {
        System.out.println(recursive_f(10));
        System.out.println(dp_f(10));
    }

    /**
     * 递归-求第 n 个斐波那契数
     */
    private static int recursive_f(int n) {
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;
        return recursive_f(n-1) + recursive_f(n-2);
    }

    /**
     * 动态规划-求第 n 个斐波那契数
     */
    private static int dp_f(int n) {
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;

        int first = 0; // n-1
        int second = 1; // n-2
        int fn = 0;
        for (int i = 2; i <= n; i++) {
            fn = first + second;
            first = second;
            second = fn;
        }
        return fn;
    }
}
