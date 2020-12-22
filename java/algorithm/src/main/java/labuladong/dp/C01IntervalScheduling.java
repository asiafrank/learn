package labuladong.dp;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 区间调度
 *
 * @author zhangxiaofan 2020/12/22-13:37
 */
public class C01IntervalScheduling {

    /**
     * 算出这些区间中最多有⼏个互不相交的区间。
     *
     * 贪心：
     * 1.从 intervals 取 end 最小的区间 A
     * 2.剩下的区间剔除所有和 A 区间重叠的区间
     * 3.重复 1 步骤，直到 intervals 为空。
     * 4.每次取最小的 end 的区间合并成一个集合作为解。
     *
     * @param intervals 区间
     * @return 不相交的区间的个数
     */
    public static int intervalSchedule(int[][] intervals) {
        if (intervals == null)
            return 0;

        Arrays.sort(intervals, Comparator.comparingInt(a -> a[1])); // end 从小到大排序
        int len = intervals.length;
        int[] curr = intervals[0]; // 当前取出的 end 最小的区间

        int count = 1;
        for (int i = 1; i < len; i++) {
            int currEnd = curr[1];
            int[] item = intervals[i];
            int itemStart = item[0];

            if (itemStart >= currEnd) { // 区间不相交, 等于也算不相交
                curr = item;
                count++;
            }
        }
        return count;
    }

    /**
     * LeetCode 435 无重叠区间
     * 给定一个区间的集合，找到移除重叠区间的最小数量，使剩余区间互不重叠。
     * 注：
     *   1.可以认为区间的结束总是大于区间的开始
     *   2.区间[1,2]和[2,3]的边界相互"接触"，但没有相互重叠
     *
     * 该问题本质就是 总区间个数 - 最大不相交的区间的个数
     *
     * @return 移除重叠区间的最小数量
     */
    public static int eraseOverlapIntervals(int[][] intervals) {
        int len = intervals.length;
        return len - intervalSchedule(intervals);
    }

    /**
     * leetcode 452. 用最少数量的箭引爆气球
     * 在二维空间中有许多球形的气球。对于每个气球，提供的输入是水平方向上，气球直径的开始和结束坐标。
     * 由于它是水平的，所以纵坐标并不重要，因此只要知道开始和结束的横坐标就足够了。开始坐标总是小于结束坐标。
     *
     * 一支弓箭可以沿着 x 轴从不同点完全垂直地射出。在坐标 x 处射出一支箭，
     * 若有一个气球的直径的开始和结束坐标为 xstart，xend， 且满足 xstart ≤ x ≤ xend，
     * 则该气球会被引爆。可以射出的弓箭的数量没有限制。 弓箭一旦被射出之后，可以无限地前进。
     * 我们想找到使得所有气球全部被引爆，所需的弓箭的最小数量。
     *
     * 给你一个数组 points ，其中 points [i] = [xstart,xend] ，返回引爆所有气球所必须射出的最小弓箭数。
     *
     * 本质上这题和区间调度一样，只要找出最多有几个不重叠区间，就是最少使用的箭个数
     * 唯一不同点是，[1,2]和[2,3]的边界相互"接触"，也可被一个箭射爆
     *
     * 示例 1：
     *
     * 输入：points = [[10,16],[2,8],[1,6],[7,12]]
     * 输出：2
     * 解释：对于该样例，x = 6 可以射爆 [2,8],[1,6] 两个气球，以及 x = 11 射爆另外两个气球
     */
    public static int findMinArrowShots(int[][] points) {
        if (points == null || points.length == 0)
            return 0;

        Arrays.sort(points, Comparator.comparingInt(a -> a[1])); // 按 end 排序

        int len = points.length;
        int[] curr = points[0];
        int count = 1;
        for (int i = 1; i < len; i++) {
            int currEnd = curr[1];
            int[] item = points[i];
            int itemStart = item[0];
            if (itemStart > currEnd) { // 必须 itemStart 大于 currEnd 才算不重叠
                count++;
                curr = item;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        test1();
        test2();
    }

    private static void test1() {
        int[][] intervals = new int[][] {
                new int[] {1, 3},
                new int[] {2, 3},
                new int[] {3, 5},
                new int[] {3, 4},
                new int[] {5, 8},
                new int[] {5, 9},
        };
        int i = intervalSchedule(intervals);
        System.out.println(i); // 3

        int i1 = eraseOverlapIntervals(intervals);
        System.out.println(i1); // 3
    }

    private static void test2() {
        // [[10,16],[2,8],[1,6],[7,12]]
        int[][] points = new int[][] {
                new int[]{10, 16},
                new int[]{2, 8},
                new int[]{1, 6},
                new int[]{7, 12}
        };
        int i = findMinArrowShots(points);
        System.out.println(i); // 2
    }
}
