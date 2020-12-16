package labuladong.dp;

import java.util.Arrays;

/**
 * 最长递增子序列(Longest Increasing Subsequence LIS)
 * <p>
 * 给一个无序的整数数组，求这个数组的最长上升子序列
 * 如：[10,9,2,5,3,7,101,18]
 * 它的最长上升子序列是 [2,3,7,101]，长度为 4
 *
 * @author zhangxiaofan 2020/12/16-10:15
 */
public class C01LongestIncreasingSubsequence {

    // O(n^2)
    public static int lis(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;

        // dp[i] = x 的含义是以下标 i 元素为结尾的最长上升子序列长度为 x
        //           也就是说  nums[i] 包含在最长上升子序列里时，dp[i] = x
        //                    否则 dp[i] = 1
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1); // 默认都是 1
        for (int i = 1; i < nums.length; i++) { // i 从第一个元素开始
            for (int j = 0; j < i; j++) {      // j 0 到 i - 1 遍历，找到 dp[j] 求最大值
                if (nums[i] > nums[j]) { // 递增, 长度+1，找大值
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                }
            }
        }

        // 每个元素为结尾的LIS已经算好；现在只需找出 dp 里的最大值即可
        int max = 0;
        for (int e : dp) {
            max = Math.max(max, e);
        }
        return max;
    }

    // 二分查找解法, patience game 的纸牌排序，patience sorting


    public static void main(String[] args) {
        int[] nums = new int[]{10, 9, 2, 5, 3, 7, 101, 18};
        int lis = lis(nums);
        System.out.println(lis);
    }
}
