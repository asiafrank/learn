package com.asiafrank.se.classload;

import com.asiafrank.se.constant.Constants;

/**
 * Created by Xiaofan Zhang on 26/2/2016.
 */
public class ClassLoadTest {

    public static void main(String[] args) {
        String str = Constants.JOB + "|" + Constants.NAME;
        System.out.println("str: " + str);

        System.out.println("========");
        new Constants();
    }
}
