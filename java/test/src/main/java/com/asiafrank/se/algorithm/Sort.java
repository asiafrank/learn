package com.asiafrank.se.algorithm;

public class Sort {
    public static void main(String[] args) {
        int[] a = {9, 7, 10, 8, 6, 1, 3, 2, 4, 5};
        insertionSort(a);
        print(a);
    }

    private static void selectionSort(int[] arr) {
        int n = arr.length;
        int minPos;
        int temp;
        for (int i = 0; i < n; i++) {
            minPos = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minPos]) {
                    minPos = j;
                }
            }
            // exchange
            temp = arr[i];
            arr[i] = arr[minPos];
            arr[minPos] = temp;
        }
    }

    private static void insertionSort(int[] arr) {
        int n = arr.length;
        int temp;
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j-1]) {
                    // exchange
                    temp = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = temp;
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
