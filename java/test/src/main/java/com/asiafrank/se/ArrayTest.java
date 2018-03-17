package com.asiafrank.se;

/**
 * Created by Xiaofan Zhang on 2/2/2016.
 */
public class ArrayTest {
    public static void main(String[] args) {
        Integer[] a = {0, 1, 2, 3};
        System.out.println("pre: " + arrayToString(a));
        Integer[] b = process(a);
        System.out.println("after: " + arrayToString(a));
        System.out.println("process return: " + arrayToString(b));
    }

    private static Integer[] process(Integer[] a) {
        int len = a.length;
        Integer[] c = new Integer[len];

        for (int m = 0; m < len; m++) {
            c[m] = a[m];
        }

        for (int i = 0; i < len; i++) {
            a[i] = c[len - i - 1];
        }
        return a;
    }

    public static String arrayToString(Integer[] a) {
        StringBuffer s = new StringBuffer();
        for (Integer x : a) {
            s.append(x + " ");
        }
        return s.toString();
    }
}
