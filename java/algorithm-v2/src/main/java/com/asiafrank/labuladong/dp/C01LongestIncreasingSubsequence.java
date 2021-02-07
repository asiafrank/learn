package com.asiafrank.labuladong.dp;

/**
 * 最长公共子序列
 * 求最长公共子序列长度
 *
 * @author zhangxiaofan 2021/02/07-16:59
 */
public class C01LongestIncreasingSubsequence {
    /**
     * dp[i] = x 的含义是第 i 个位置的元素 num[i] 为结尾的最长公共子序列长度为 x
     * @param nums 数组
     * @return 最长公共子序列的长度
     */
    public static int lisLength(int[] nums) {
        int[] dp = new int[nums.length]; // 初始都是 0
        dp[0] = 1;

        // 计算 dp 每个元素的值
        for (int i = 1; i < nums.length; i++) {
            int curr = nums[i];
            // 找到小于 curr 的，长度最长的值 就是 dp[i]
            for (int j = 0; j < nums.length; j++) {
                if (nums[j] < curr) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        int res = 0;
        for (int i = 0; i < dp.length; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = new int[] {1, 4, 3, 4, 2, 3};
        int len = lisLength(nums);
        System.out.println(len); //
    }
}
