package com.asiafrank.se;

/**
 * Created by Xiaofan Zhang on 29/2/2016.
 */
public class IntCount {
    public static void main(String[] args) {
        int n = 2;
        n = n & (n - 1);
        System.out.println(n);
    }
}
