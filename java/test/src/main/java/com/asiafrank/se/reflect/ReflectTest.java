package com.asiafrank.se.reflect;

import java.lang.reflect.Field;

/**
 * Created by Xiaofan Zhang on 16/2/2016.
 */
public class ReflectTest {
    public static void main(String[] args) {
        int count = 21;
        int pageSize = 10;
        int totalPage = count / pageSize + 1;
        System.out.println("total page: " + totalPage);

        Class<Integer> i = Integer.class;
        Field[] fs = i.getDeclaredFields();
        for (Field f : fs) {
            System.out.println(f.getName());
        }

        Long a = 1000l;
        Integer b = Integer.valueOf(a.intValue());
    }
}
