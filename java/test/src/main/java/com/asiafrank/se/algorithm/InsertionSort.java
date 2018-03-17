package com.asiafrank.se.algorithm;

public class InsertionSort {
    public static void main(String[] args) {
        int[] a = {9, 7, 10, 8, 6, 1, 3, 2, 4, 5};
        sort(a);
        print(a);
    }

    private static void sort(int[] a) {
        int t;
        for (int i = 0; i < a.length; i++) {
            for (int j = i; j > 0; j--) {
                if (a[j] < a[j-1]) {
                    t = a[j];
                    a[j] = a[j-1];
                    a[j-1] = t;
                }
            }
        }
    }

    private static void print(int[] a) {
        if (a == null) {
            throw new NullPointerException("array a should not be null");
        }

        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }
}
