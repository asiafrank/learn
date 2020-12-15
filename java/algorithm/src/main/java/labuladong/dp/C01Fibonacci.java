package labuladong.dp;

/**
 * 斐波那契数列 引导 动态规划思想
 * @author zhangxiaofan 2020/12/15-09:35
 */
public class C01Fibonacci {

    /**
     * 斐波那契数列 递归形式
     */
    public static int f(int n) {
        if (n == 1 || n == 2)
            return 1;
        return f(n-1) + f(n-2);
    }

    /**
     * 记忆法，如果子问题重叠，则从 dp 表中获取
     * 自顶向下
     */
    public static int fMemoTop2Down(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 1;
        return doFMemo0(n, dp);
    }

    private static int doFMemo0(int n, int[] dp) {
        if (n == 1 || n == 2) {
            dp[n] = 1;
            return 1;
        }

        int rs = dp[n];
        if (rs == 0) { // 0 代表没有计算过
            dp[n] = doFMemo0(n - 1, dp) + doFMemo0(n - 2, dp);
            rs = dp[n];
        }
        return rs; // 算过就不用再算了
    }

    /**
     * 记忆法，如果子问题重叠，则从 dp 表中获取
     * 自底向上
     */
    public static int fMemoDown2Top(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 1;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    /**
     * 斐波那契数列 for 循环递推
     * 最终版本
     */
    public static int fFinal(int n) {
        if (n == 0)
            return 0;
        if (n == 1 || n == 2)
            return 1;

        int pre = 1;
        int curr = 1;
        for (int i = 3; i <= n; i++) {
            int sum = curr + pre;
            pre = curr;
            curr = sum;
        }
        return curr;
    }

    public static void main(String[] args) {
        int rs = 55, n = 10;
        int f = f(n);
        int fMemoDown2Top = fMemoDown2Top(n);
        int fMemoTop2Down = fMemoTop2Down(n);
        int fFinal = fFinal(n);
        System.out.println("rs = " + rs + ", " + ((rs == f)
                && (rs == fMemoDown2Top)
                && (rs == fMemoTop2Down)
                && (rs == fFinal)));
    }
}
