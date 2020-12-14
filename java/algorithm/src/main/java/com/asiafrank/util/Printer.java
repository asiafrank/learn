package com.asiafrank.util;

import java.util.ArrayDeque;
import java.util.Deque;

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
}
