package com.asiafrank.labuladong.dp;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 给你很多形如 [start, end] 的闭区间，请你设计一个算法，算出这些区间中最多有几个互不相交的区间。
 *
 * int intervalSchedule(int[][] intvs) {}
 * 举个例子，intvs = [[1,3], [2,4], [3,6]]，这些区间最多有 2 个区间互不相交，
 * 即 [[1,3], [3,6]]，你的算法应该返回 2。
 * 注意边界相同并不算相交。
 *
 * @author zhangxiaofan 2021/02/11-14:41
 */
public class C04IntervalSchedule {

    public static int intervalSchedule(int[][] intervals) {
        if (intervals == null || intervals.length == 0)
            return 0;

        // 1. 排序
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[1])); // end 从小到大排序

        int len = intervals.length;
        int[] prev = intervals[0];
        int count = 1;

        // 2. 遍历记录不相交个数
        for (int i = 1; i < len; i++) {
            int[] curr = intervals[i];
            int currStart = curr[0];
            int prevEnd = prev[1];
            if (currStart >= prevEnd) { // 找到一个不相交的
                count++;
                prev = curr;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        int[][] intervals = new int[][] {
                new int[] {1, 3},
                new int[] {2, 4},
                new int[] {3, 6},
        };
        int i = intervalSchedule(intervals);
        System.out.println(i);
    }
}
