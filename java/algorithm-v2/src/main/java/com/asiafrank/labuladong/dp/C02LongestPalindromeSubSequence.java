package com.asiafrank.labuladong.dp;

/**
 * 求一个字符串最长回文子序列长度
 *
 * @author zhangxiaofan 2021/02/11-11:38
 */
public class C02LongestPalindromeSubSequence {

    /**
     * 最长回文子序列长度
     *
     * 递推式：
     * if s[i] == s[j]
     *   dp[i][j] = dp[i+1][j-1] + 2
     * else
     *   dp[i][j] = max(dp[i+1][j], dp[i][j-1])
     *
     * @param str 字符串
     * @return 返回 s 字符串最长回文子序列的长度
     */
    public static int longestPalindromeSubSeq(String str) {
        if (str == null || str.isEmpty())
            return 0;

        char[] s = str.toCharArray();
        int len = s.length;
        // dp[i][j] = x 含义：i 到 j 的最长回文子序列长度为 x
        int[][] dp = new int[len][len];

        // 初始化 dp[i][j], i == j 时的最长回文子序列长度
        for (int i = 0; i < len; i++) {
            dp[i][i] = 1;
        }

        // 目标是 dp[0][len-1]
        // 斜着遍历
        for (int l = 1; l < len; l++) {
            for (int i = 0; i < len; i++) {
                int j = i + l;
                if (j == len)
                    break;

                if (s[i] == s[j]) {
                    dp[i][j] = dp[i+1][j-1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
                }
            }
        }
        return dp[0][len-1];
    }

    public static void main(String[] args) {
        String str = "abcba"; // 5
        int l = longestPalindromeSubSeq(str);
        System.out.println(l);

        str = "xaboociba"; // 6
        l = longestPalindromeSubSeq(str);
        System.out.println(l);
    }
}
