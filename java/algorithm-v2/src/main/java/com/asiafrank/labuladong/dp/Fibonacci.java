package com.asiafrank.labuladong.dp;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 动态规划套路的引出
 *
 * 斐波那契数列，穷举->记忆化搜索->
 *
 * @author zhangxiaofan 2021/02/03-15:45
 */
public class Fibonacci {

    /**
     * 计算第 n 个斐波那契数列
     * @param n 第几个
     * @return 第 n 个斐波那契数列的值
     */
    public static int f(int n) {
        if (n == 1 || n == 2)
            return 1;
        return f(n - 2) + f(n - 1);
    }

    public static int fMemo(int n) {
        // key: 计算第几个, value: 值
        Map<Integer, Integer> memo = new HashMap<>();
        memo.put(1, 1);
        memo.put(2, 1);
        return fMemo0(n, memo);
    }

    private static int fMemo0(int n, Map<Integer, Integer> memo) {
        Integer v = memo.get(n);
        if (Objects.nonNull(v))
            return v;

        v = fMemo0(n - 2, memo) + fMemo0(n - 1, memo);
        memo.put(n, v);
        return v;
    }

    /**
     * 类 DP 的迭代算法, 自底向上
     *
     * @param n 第几个
     */
    public static int fDP(int n) {
        int fn_1 = 1;
        int fn_2 = 1;
        int fn = 0;
        for (int i = 3; i <= n; i++) {
            fn = fn_1 + fn_2;
            fn_2 = fn_1;
            fn_1 = fn;
        }
        return fn;
    }

    public static void main(String[] args) {
        System.out.println(f(10));
        System.out.println(fMemo(10));
        System.out.println(fDP(10));
    }
}
