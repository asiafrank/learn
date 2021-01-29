package com.example.demo;

import org.junit.jupiter.api.Test;
import org.roaringbitmap.RoaringBitmap;

public class RoaringBitmapTest {
    @Test
    public void test() {
        RoaringBitmap rr = RoaringBitmap.bitmapOf(1,2,3,1000);
//        rr.deserialize();
    }
}
