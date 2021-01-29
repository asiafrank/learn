package com.example.appoperation;

import org.junit.Test;
import org.roaringbitmap.RoaringBitmap;

/**
 * @author zhangxiaofan 2021/01/15-17:08
 */
public class RoaringBitmapTest {

    @Test
    public void test() {
        RoaringBitmap roaringBitmap = new RoaringBitmap();
        roaringBitmap.add(1);
        roaringBitmap.add(10);
        roaringBitmap.add(100);
        roaringBitmap.add(1000);
        roaringBitmap.add(10000);
        for (int i = 65536; i < 65536*2; i+=2) {
            roaringBitmap.add(i);
        }
        roaringBitmap.add(65536L*3, 65536L*4);
        roaringBitmap.runOptimize();
    }

    /**
     * 看看 10亿 规模的用户 id能压缩多大
     * 10亿userId 用 bitmap 表示，需要用 10亿/8/1024/1024≈119MB，
     *
     * 10亿用 RoaringBitmap 试试
     *
     * RoaringBitmap 的好处: 假设 10亿用户
     * 1. 单纯用bitmap，如果给每个标签都分配 10亿的 bit 位，那当出现1000 个标签的时候，就 1000 * 119MB= 116GB 了
     * 2. 使用 RoaringBitmap 能按集合的大小逐步扩容，更省空间。
     *
     * RoaringBitmap 原理见：https://blog.csdn.net/tonywu1992/article/details/104746214
     *
     * 实验结果：10亿里占用 1/3 的 userId 放入 roaringBitmap 中，占用空间 125123808 byte = 119MB
     */
    @Test
    public void sizeTest() {
        int userIdCount = 10_0000_0000; // 10 亿
        RoaringBitmap roaringBitmap = new RoaringBitmap();
        for (int i = 0; i < userIdCount; i+=2) { // 步长为 2
            roaringBitmap.add(i);
        }
        roaringBitmap.runOptimize();
        int size = roaringBitmap.serializedSizeInBytes();
        System.out.println("size: " + size + "byte"); // 119MB
    }

    @Test
    public void size10wTest() {
        int userIdCount = 30_0000; // 30 万
        RoaringBitmap roaringBitmap = new RoaringBitmap();
        for (int i = 0; i < userIdCount; i+=2) { // 步长为 2, 也就是将近 10万 userId 进入 roaringBitmap
            roaringBitmap.add(i);
        }
        roaringBitmap.runOptimize();
        int size = roaringBitmap.serializedSizeInBytes();
        System.out.println("size: " + size + "byte"); // 40KB
    }
}
