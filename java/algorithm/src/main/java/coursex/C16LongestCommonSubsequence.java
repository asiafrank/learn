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
 * TODO:
 */
public class C16LongestCommonSubsequence {
}
