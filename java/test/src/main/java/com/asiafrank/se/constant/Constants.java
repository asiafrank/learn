package com.asiafrank.se.constant;

/**
 * Created by Xiaofan Zhang on 26/2/2016.
 */
public final class Constants {
    public static final String NAME;
    public static final String JOB;

    public int i = getInteger();

    static {
        NAME = "ASIAFRANK";
        JOB = "PROGRAMMER";
        System.out.println("Constants: " + NAME + " " + JOB);
    }

    public Constants() {
        System.out.println("Constants initialized");
    }

    public int getInteger() {
        System.out.println("Constant getInteger");
        return 150;
    }
}
