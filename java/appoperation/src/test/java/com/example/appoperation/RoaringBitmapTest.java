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
}
