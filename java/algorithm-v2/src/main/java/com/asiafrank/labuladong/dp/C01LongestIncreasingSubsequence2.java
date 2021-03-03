package com.asiafrank.labuladong.dp;

/**
 * 最长递增子序列长度
 * @author zhangxiaofan 2021/03/01-16:19
 */
public class C01LongestIncreasingSubsequence2 {
    /**
     * 求 nums 的最长递增子序列
     * @param nums 数组
     * @return 最长递增子序列长度
     */
    public static int longestIncreasingSubsequence(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // dp[i] = x 含义是：以 i 为结尾的最长递增子序列长度是 x
        int[] dp = new int[nums.length];
        // base case
        for (int i = 0; i < nums.length; i++) {
            dp[i] = 1;
        }

        for (int i = 1; i < nums.length; i++) {
            int x = nums[i];
            for (int j = 0; j < i; j++) {
                int y = nums[j];
                if (y < x) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        int rs = 0;
        for (int i = 0; i < dp.length; i++) {
            rs = Math.max(dp[i], rs);
        }

        return rs;
    }

    public static void main(String[] args) {
        int[] nums = new int[] {1, 4, 3, 4, 2, 3};
        int len = longestIncreasingSubsequence(nums);
        System.out.println(len); // 3
    }
}
