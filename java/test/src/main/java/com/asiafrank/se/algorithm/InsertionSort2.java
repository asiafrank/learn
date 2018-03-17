package com.asiafrank.se.algorithm;

public class InsertionSort2 {
    public static void main(String[] args) {
        int[] a = {9, 7, 10, 8, 6, 1, 3, 2, 4, 5};
        sort(a);
        print(a);
    }

    private static void sort(int[] arr) {
        int i, j;
        int temp = 0;
        int len = arr.length;
        for (i = 1; i < len; i++) {
            temp = arr[i];
            j = i - 1;
            for (;j >= 0 && arr[j] > temp; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = temp;
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
