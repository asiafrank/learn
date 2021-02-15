package com.asiafrank.algs4;

import com.asiafrank.utils.Printer;

/**
 * 冒泡排序
 * O(n^2)
 * 稳定
 * @author zhangxiaofan 2021/02/14-15:48
 */
public class BubbleSort {
    /**
     * 冒泡排序
     * 大的数下沉，小的数上浮
     */
    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        for (int end = arr.length - 1; end > 0; end--) {
            for (int i = 1; i <= end; i++) {
                int prev = arr[i - 1];
                int curr = arr[i];
                if (prev > curr) {
                    exch(arr, i - 1, i);
                }
            }
        }
    }

    private static void exch(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};
        bubbleSort(arr);
        Printer.printArray(arr);
    }

}
