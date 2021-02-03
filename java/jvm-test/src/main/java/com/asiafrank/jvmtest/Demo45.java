package com.asiafrank.jvmtest;

/**
 * GC实验
 * 动态年龄判断
 */
public class Demo45 {
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

在这些参数里我们注意几点，新生代我们通过“-XX:NewSize”设置为10MB了
然后其中Eden区是8MB，每个Survivor区是1MB，Java堆总大小是20MB，
老年代是10MB，大对象必须超过10MB才会直接进入老年代
但是我们通过“-XX:MaxTenuringThreshold=15”设置了，
只要对象年龄达到15岁才会直接进入老年代。
     */

    public static void main(String[] args) {
        byte[] array1 = new byte[2 * 1024 * 1024]; // 2MB
        array1 = new byte[2 * 1024 * 1024];
        array1 = new byte[2 * 1024 * 1024];
        array1 = null; // 上面 6MB 是垃圾

        byte[] array2 = new byte[128 * 1024]; // 128KB
        byte[] array3 = new byte[2 * 1024 * 1024]; // 2MB

        array3 = new byte[2 * 1024 * 1024]; // 2MB
        array3 = new byte[2 * 1024 * 1024]; // 2MB
        array3 = new byte[128 * 1024]; // 128KB
        array3 = null;

        byte[] array4 = new byte[2 * 1024 * 1024]; // 2Mb
        // 触发第二次 Young GC，动态年龄判断，晋升到老年代
    }
}
