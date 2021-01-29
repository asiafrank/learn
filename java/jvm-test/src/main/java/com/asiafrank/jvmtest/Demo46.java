package com.asiafrank.jvmtest;

/**
 * GC实验
 * Eden区不够分配，Young GC 后直接进入老年代
 */
public class Demo46 {
    /*
-XX:NewSize=10485760
-XX:MaxNewSize=10485760
-XX:InitialHeapSize=20971520
-XX:MaxHeapSize=20971520
-XX:SurvivorRatio=8
-XX:MaxTenuringThreshold=15
-XX:PretenureSizeThreshold=10485760
-XX:+UseParNewGC
-XX:+UseConcMarkSweepGC
-XX:+PrintGCDetails
-XX:+PrintGCTimeStamps
-Xloggc:gc.log
     */

    public static void main(String[] args) {
        byte[] array1 = new byte[2 * 1024 * 1024]; // 2MB
        array1 = new byte[2 * 1024 * 1024];
        array1 = new byte[2 * 1024 * 1024];

        byte[] array2 = new byte[128 * 1024]; // 128KB
        array2 = null;

        byte[] array3 = new byte[2 * 1024 * 1024]; // 2MB
        // 触发 GC，array3 的对象分配，Eden区空间不够，直接进入老年代
    }
}
