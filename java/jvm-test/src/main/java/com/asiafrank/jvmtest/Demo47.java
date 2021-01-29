package com.asiafrank.jvmtest;

/**
 * GC实验
 * Full GC
 */
public class Demo47 {
    /*
-XX:NewSize=10485760
-XX:MaxNewSize=10485760
-XX:InitialHeapSize=20971520
-XX:MaxHeapSize=20971520
-XX:SurvivorRatio=8
-XX:MaxTenuringThreshold=15
-XX:PretenureSizeThreshold=3145728
-XX:+UseParNewGC
-XX:+UseConcMarkSweepGC
-XX:+PrintGCDetails
-XX:+PrintGCTimeStamps
-Xloggc:gc.log


大对象限制 3MB  -XX:PretenureSizeThreshold=3145728
     */

    public static void main(String[] args) {
        byte[] array1 = new byte[4 * 1024 * 1024]; // 4MB 直接进入老年代
        array1 = null;

        byte[] array2 = new byte[2 * 1024 * 1024]; // 2MB
        byte[] array3 = new byte[2 * 1024 * 1024]; // 2MB
        byte[] array4 = new byte[2 * 1024 * 1024]; // 2MB
        byte[] array5 = new byte[128 * 1024]; // 128KB, 8MB eden 区满，触发 Young GC， 6MB 进入老年代， 现在老年代 10MB

        byte[] array6 = new byte[2 * 1024 * 1024]; // 2MB
    }
}
