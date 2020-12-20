package com.asiafrank.util;

import java.util.*;

public class Printer {
    public static void printStackWithNoChange(Deque<Integer> stack) {
        Deque<Integer> s = new ArrayDeque<>(stack);

        StringBuilder sb = new StringBuilder("[");
        int n = s.size() - 1;
        int i = 0;
        while (!s.isEmpty()) {
            Integer pop = s.pop();
            sb.append(pop);
            if (i < n) {
                sb.append(",");
            }
            i++;
        }
        sb.append("]");
        System.out.println(sb.toString());
    }

    public static void printArray(int[] arr) {
        StringBuilder sb = new StringBuilder("[");
        int n = arr.length - 1;
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < n) {
                sb.append(",");
            }
        }
        sb.append("]");
        System.out.println(sb.toString());
    }

    public static void printColl(Collection<String> list) {
        System.out.println("[");
        for (String s : list) {
            System.out.println(s);
        }
        System.out.println("]");
    }

    public static void print2DArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            printArray(arr[i]);
        }
    }

    public static void main(String[] args) {
        int m = 5, n = 5;
        int[][] arr = new int[m][n];
        int num = 1;
        // 斜着遍历
        for (int l = 1; l < n; l++) {
            for (int i = 0; i < n - l; i++) {
                int j = i + l;
                arr[i][j] = num++;
            }
        }
        print2DArray(arr);

        System.out.println();

        // 斜着遍历2
        for (int i = 1; i < n; i++) {
            int row = 0;
            int col = i;
            while (row < n && col < n) {
                arr[row][col]++;
                row++;
                col++;
            }
        }
        print2DArray(arr);
    }
}
