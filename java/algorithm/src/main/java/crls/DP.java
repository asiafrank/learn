package crls;

import java.util.Arrays;

/**
 * 动态规划-P204
 *
 * @author zhangxf created at 1/21/2019.
 */
public class DP {

    public static class Pair {
        public Pair(int[][] m, int[][] s) {
            this.m = m;
            this.s = s;
        }

        public int[][] m;
        public int[][] s;
    }

    //---------------------------------------
    // 矩阵链乘法
    //---------------------------------------

    /**
     * 矩阵链乘法
     *
     * @param p 矩阵规模序列
     * @return 计算结果 m 和 划分点 s
     */
    public static Pair matrixChainOrder(int[] p) {
        int n = p.length - 1;
        //  m[1..n][1..n]
        int[][] m = new int[n+1][n+1];
        //  s[1..n-1][2..n]
        int[][] s = new int[n][n+1];
        for (int i = 0; i <= n; i++)
            m[i][i] = 0;

        for (int l = 2; l <= n; l++) {
            int end = n - l + 1;
            for (int i = 1; i <= end; i++) {
                int j = i + l - 1;
                m[i][j] = Integer.MAX_VALUE;
                for (int k = i; k <= j - 1; k++) {
                    int q = m[i][k] + m[k+1][j] + p[i-1] * p[k] * p[j];
                    if (q < m[i][j]) {
                        m[i][j] = q;
                        s[i][j] = k;
                    }
                }
            }
        }

        return new Pair(m, s);
    }

    public static void printOptimalParens(int[][] s, int i, int j) {
        if (i == j)
            System.out.print("A"+ i);
        else {
            System.out.print("(");
            printOptimalParens(s, i, s[i][j]);
            printOptimalParens(s, s[i][j]+1, j);
            System.out.print(")");
        }
    }

    //---------------------------------------
    // 公共最长子序列
    //---------------------------------------

    public static class LCSPair {
        public LCSPair(char[][] b, int[][] c) {
            this.b = b;
            this.c = c;
        }

        char[][] b;
        int[][] c;
    }

    public static LCSPair lcsLength(int[] x, int[] y) {
        int m = x.length - 1;
        int n = y.length - 1;
        char[][] b = new char[m+1][n+1]; // 帮助构造最优解的路径表
        int[][] c = new int[m+1][n+1]; // 对于 x[i] y[j] 的最长公共子序列长度为 c[i][j]

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (x[i] == y[j]) {
                    c[i][j] = c(c, i-1, j-1) + 1;
                    b[i][j] = '↖';
                } else if (c(c, i-1, j) >= c(c, i, j-1)) { // z[k]!=x[i] z[k]=y[j] 的情况
                    c[i][j] = c(c, i-1, j);
                    b[i][j] = '↑';
                } else { // z[k]=x[i] z[k]!=y[j] 的情况
                    c[i][j] = c(c, i, j-1);
                    b[i][j] = '←';
                }
            }
        }
        return new LCSPair(b, c);
    }

    /**
     * 取消算法导论伪代码中对 c[i][0] 和 c[0][j] 的初始化，并且传入的 x，y 数组下表从 0 开始
     */
    private static int c(int[][] c, int i, int j) {
        if (i < 0 || j < 0)
            return 0;
        return c[i][j];
    }

    public static void printLCS(char[][] b, int[] x, int i, int j) {
        if (i < 0 || j < 0)
            return;

        if (b[i][j] == '↖') {
            printLCS(b, x, i - 1, j - 1);
            System.out.print(x[i]);
        } else if (b[i][j] == '↑') {
            printLCS(b, x, i - 1, j);
        } else {
            printLCS(b, x, i, j - 1);
        }
    }
}
