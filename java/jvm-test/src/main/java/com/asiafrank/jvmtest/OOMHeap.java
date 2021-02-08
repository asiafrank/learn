package com.asiafrank.jvmtest;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆溢出
 */
public class OOMHeap {

    /*
实验参数：
-Xms10m
-Xmx10m
     */

    public static void main(String[] args) {
        long count = 0;
        List<Object> list = new ArrayList<>();
        while (true) {
            list.add(new Object());
            System.out.println("当前创建了第" + (++count) + "个对象");
        }
    }
}
