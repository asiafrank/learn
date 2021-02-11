package com.asiafrank.labuladong.dp;

/**
 * 求两个字符串的最长公共子序列
 *
 * @author zhangxiaofan 2021/02/11-12:05
 */
public class C03LongestCommonSubSequence {
    /**
     * 求最长公共子序列的长度
     * dp[i][j] = x 的含义 s1 0 到 i 与 s2 0 到 j 的字符串，最长公共子序列长度为 x
     * 目标：dp[str1.length][str2.length]
     *
     * 递推式：
     * if s1[i] == s2[j]
     *    dp[i][j] = dp[i-1][j-1] + 1
     * else
     *    dp[i][j] = max(dp[i-1][j], dp[i][j-1])
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return str1 与 str2 的最长公共子序列长度
     */
    public static int lcs(String str1, String str2) {
        if (str1 == null || str1.isEmpty())
            return 0;
        if (str2 == null || str2.isEmpty())
            return 0;

        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();

        int s1Len = s1.length;
        int s2Len = s2.length;

        int[][] dp = new int[s1Len + 1][s2Len + 1];
        // 0 为空串的 base case
        // 从 1 开始下标才是 s1 和 s2 字符串的开始0
        // base case
        for (int i = 0; i <= s1Len; i++) {
            dp[i][0] = 0;
        }
        for (int j = 0; j <= s2Len; j++) {
            dp[0][j] = 0;
        }

        for (int i = 1; i <= s1Len; i++) {
            int s1_i = i - 1;
            for (int j = 1; j <= s2Len; j++) {
                int s2_j = j - 1;
                if (s1[s1_i] == s2[s2_j]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        Printer.print2DArray(dp);
        return dp[s1Len][s2Len];
    }

    /**
     * 尝试直接返回最长公共子序列
     */
    public static String lcsStr(String str1, String str2) {
        if (str1 == null || str1.isEmpty())
            return "";
        if (str2 == null || str2.isEmpty())
            return "";

        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();

        int s1Len = s1.length;
        int s2Len = s2.length;

        int[][] dp = new int[s1Len + 1][s2Len + 1];
        char[][] path = new char[s1Len + 1][s2Len + 1]; // 用于记录匹配路径

        // 0 为空串的 base case
        // 从 1 开始下标才是 s1 和 s2 字符串的开始0
        // base case
        for (int i = 0; i <= s1Len; i++) {
            dp[i][0] = 0;
        }
        for (int j = 0; j <= s2Len; j++) {
            dp[0][j] = 0;
        }

        for (int i = 1; i <= s1Len; i++) {
            int s1_i = i - 1;
            for (int j = 1; j <= s2Len; j++) {
                int s2_j = j - 1;
                if (s1[s1_i] == s2[s2_j]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    path[i][j] = '\\'; // '\' 表示 i-1,j-1 得到 i,j
                } else {
                    int above = dp[i - 1][j];
                    int left = dp[i][j - 1];
                    int max = above;
                    if (above >= left) {
                        path[i][j] = '|'; // '|' 表示由 i-1,j 得到 i,j
                    } else {
                        max = left;
                        path[i][j] = '<'; // '<' 表示由 i,j-1 得到 i,j
                    }
                    dp[i][j] = max;
                }
            }
        }

        Printer.print2DArray(dp);
        Printer.print2DArray(path);

        // 还原一个最长公共子序列
        StringBuilder sb = new StringBuilder();
        recover(path, s1Len, s2Len, s1, s2, sb);
        return sb.toString();
    }

    private static void recover(char[][] path, int i, int j, char[] s1, char[] s2, StringBuilder sb) {
        char c = path[i][j];
        if (c == '\\') {
            recover(path, i - 1, j - 1, s1, s2, sb);
            sb.append(s1[i - 1]);
        } else if (c == '|') {
            recover(path, i - 1, j, s1, s2, sb);
        } else if (c == '<') {
            recover(path, i, j - 1, s1, s2, sb);
        }
    }

    public static void main(String[] args) {
        String s1 = "xaxbxcx";
        String s2 = "cacbcd";
        int lcs = lcs(s1, s2);
        System.out.println(lcs); // 3

        String s = lcsStr(s1, s2);
        System.out.println(s);
    }
}
