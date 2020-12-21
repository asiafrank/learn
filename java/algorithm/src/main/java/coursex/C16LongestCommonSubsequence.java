package coursex;

/**
 * 多样本位置全对应的尝试模型
 *
 * 最长公共子序列
 * 两个子序列 X，Y。求这两个序列的最长公共子序列
 *
 * 暴力方法：
 * 1.列出 X 的所有子序列，列出 Y 的所有子序列
 * 2.找出最长的公共子序列
 *
 * 动态规划：
 * 设 dp[i][j] = c 的含义是[0->i] 串与 [0->j] 的最长公共子序列长度
 * 两种情况
 * 1. X[i] != Y[j]，则 dp[i][j] = max(dp[i-1][j], dp[i][j-1])
 * 2. X[i] == Y[j]，则 dp[i][j] = dp[i-1][j-1] + 1
 *
 */
public class C16LongestCommonSubsequence {

    // O(NM) N 为 x 数组的规模，M 为 y 数组的规模
    public static int lcs(String str1, String str2) {
        if (str1 == null || str2 == null)
            return 0;

        if (str1.isEmpty() || str2.isEmpty())
            return 0;

        char[] x = str1.toCharArray();
        char[] y = str2.toCharArray();

        int[][] dp = new int[x.length][y.length];
        // 初始化 dp[i][0], x[i] 与 y[0] 一个一个比较
        for (int i = 0; i < x.length; i++) {
            dp[i][0] = x[i] == y[0] ? 1 : 0;
        }
        // 初始化 dp[0][j], y[i] 与 x[0] 一个一个比较
        for (int j = 0; j < y.length; j++) {
            dp[0][j] = y[j] == x[0] ? 1 : 0;
        }

        // 维护 dp 表
        for (int i = 1; i < x.length; i++) {
            for (int j = 1; j < y.length; j++) {
                if (x[i] == y[j]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[x.length - 1][y.length - 1]; // 最后一个格子，就是解
    }

    public static void main(String[] args) {
        String s1 = "abcab";
        String s2 = "bc1a1b2c";
        int lcs = lcs(s1, s2);
        System.out.println(lcs); // lcs = 4, 也就是 cabc
    }
}
