package com.asiafrank.se.algorithm;

import java.util.Arrays;

public class BinarySearch {
    public static void main(String[] args) {
        int[] a = {8, 2, 3, 6, 1, 5, 4, 7, 9, 0};
        Arrays.sort(a);
        print(a);
        int index = find(9, a);
        System.out.println("\nindex: " + index);
    }

    private static int find(int target, int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int lo = 0;
        int hi = arr.length - 1;
        int mid = (hi - lo) / 2;

        while (lo <= hi) {
            if (target < arr[mid]) {
                hi = mid - 1;
                mid = lo + (hi - lo) / 2;
            } else if (target > arr[mid]) {
                lo = mid + 1;
                mid = lo + (hi - lo) / 2;
            } else {
                return mid;
            }
        }
        return -1;
    }

    private static void print(int[] arr) {
        if (arr == null || arr.length == 0) return;
        System.out.print("{");
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            System.out.print(" " + arr[i]);
        }
        System.out.print("}");
    }
}
