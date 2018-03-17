package com.asiafrank.se.algorithm;

public class SelectionSort {
    public static void main(String[] args) {
        int[] a = {9, 7, 10, 8, 6, 1, 3, 2, 4, 5};
        sort(a);
        print(a);
    }

    private static void sort(int[] a) {
        int minPos, t;
        for (int i = 0; i < a.length; i++) {
            minPos = i;
            for (int j = i+1; j < a.length; j++) {
                if (a[j] < a[minPos]) {
                    minPos = j;
                }
            }
            t = a[i];
            a[i] = a[minPos];
            a[minPos] = t;
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
