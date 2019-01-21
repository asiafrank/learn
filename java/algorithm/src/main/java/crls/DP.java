package crls;

/**
 * 动态规划
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
}
