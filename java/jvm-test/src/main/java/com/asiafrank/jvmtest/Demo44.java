package com.asiafrank.jvmtest;

/**
 * GC实验
 * Young GC
 */
public class Demo44 {
    /*
-XX:NewSize=5242880
-XX:MaxNewSize=5242880
-XX:InitialHeapSize=10485760
-XX:MaxHeapSize=10485760
-XX:SurvivorRatio=8
-XX:PretenureSizeThreshold=10485760
-XX:+UseParNewGC
-XX:+UseConcMarkSweepGC
-XX:+PrintGCDetails
-XX:+PrintGCTimeStamps
-Xloggc:gc.log

上面“-XX:InitialHeapSize”和“-XX:MaxHeapSize”就是初始堆大小和最大堆大小，
“-XX:NewSize”和“-XX:MaxNewSize”是初始新生代大小和最大新生代大小，
“-XX:PretenureSizeThreshold=10485760”指定了大对象阈值是10MB。
     */

    public static void main(String[] args) {
        byte[] array1 = new byte[1024 * 1024]; // 1MB
        array1 = new byte[1024 * 1024];
        array1 = new byte[1024 * 1024];
        array1 = null; // 上面 3MB 是垃圾

        byte[] array2 = new byte[2 * 1024 * 1024]; // 2MB, 开辟这个对象时，Eden区满，Young GC
    }
}
