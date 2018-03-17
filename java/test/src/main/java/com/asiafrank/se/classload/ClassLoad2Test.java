package com.asiafrank.se.classload;

/**
 * Created by Xiaofan Zhang on 25/3/2016.
 */
public class ClassLoad2Test {
    public static ClassLoad2Test c1 = new ClassLoad2Test();
    public static ClassLoad2Test c2 = new ClassLoad2Test();

    {
        System.out.println("constructor block");
    }

    static {
        System.out.println("static block");
    }

    public static void main(String[] args) {
        ClassLoad2Test c = new ClassLoad2Test();
        ClassLoad2Test c3 = new ClassLoad2Test();
    }
}
