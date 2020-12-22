package labuladong.dp;

import com.asiafrank.util.Printer;

/**
 * 最长回文子序列
 *
 * dp[i][j] = m
 * 含义: s[i..j] 的最长回文子序列长度为 m
 *
 * 当 s[i] == s[j] 时
 *    dp[i][j] = dp[i + 1][j - 1] + 2
 * 不相等时
 *    dp[i][j] = max(dp[i + 1][j], dp[i][j - 1])
 *
 * @author zhangxiaofan 2020/12/22-10:28
 */
public class C01LongestPalindromeSubsequence {
    /**
     * 返回最长回文子序列的长度
     */
    public static int longestPalindromeSubsequence(String s) {
        if (s == null || s.isEmpty())
            return 0;

        char[] str = s.toCharArray();
        int len = str.length;
        int[][] dp = new int[len][len];

        // 初始化 i == j 时, dp[i][j] = 1
        for (int i = 0; i < len; i++) {
            dp[i][i] = 1;
        }

        for (int i = len - 1; i >= 0; i--) {
            for (int j = i + 1; j < len; j++) {
                if (str[i] == str[j]) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }

        // Printer.print2DArray(dp);
        return dp[0][len - 1];
    }

    public static void main(String[] args) {
        String s = "abxabyb"; // a b a
        int i = longestPalindromeSubsequence(s);
        System.out.println(i);
    }
}
