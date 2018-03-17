package com.asiafrank.se;

/**
 * Created by asiafrank on 10/12/2015.
 */
public class SplitTest {
    public static void main(String args[]) {
        String[] arr = "dfsdfsdfasdfasdf".split(",");
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        System.out.println("arr[0]: " + arr[0]);
    }
}
