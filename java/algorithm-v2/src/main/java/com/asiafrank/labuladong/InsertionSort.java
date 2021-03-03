package com.asiafrank.labuladong;

import com.asiafrank.utils.Printer;

/**
 * 插入排序
 * @author zhangxiaofan 2021/03/01-16:42
 */
public class InsertionSort {

    public static void insertionSort(int[] nums) {
        if (nums == null || nums.length <= 1)
            return;

        for (int i = 1; i < nums.length; i++) {
            int curr = nums[i];

            for (int j = i - 1; j >= 0; j--) {
                int y = nums[j];
                if (curr < y) {
                    int t = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = t;
                } else {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{6,5,4,3,2,1};
        insertionSort(arr);
        Printer.printArray(arr);
    }
}
