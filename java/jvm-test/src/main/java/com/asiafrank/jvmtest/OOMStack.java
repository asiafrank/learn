package com.asiafrank.jvmtest;

/**
 * 栈溢出
 *
 * 实验参数：
 * -XX:ThreadStackSize
 */
public class OOMStack {
    public static long counter = 0;

    public static void main(String[] args) {
        work();
    }

    private static void work() {
        System.out.println("目前是第" + (++counter) + "次调用方法");
        work();
    }
}
