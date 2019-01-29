package crls.dp;

/**
 * 思考题 15-2 最长回文子序列(LPS)
 *
 * @author zhangxf created at 1/29/2019.
 */
public class LongestPalindromeSequence {

    public static class LPSPair {
        /**
         * c[i][j] 代表 x_i 到 x_j 的字符串的最长回文子序列长度
         */
        public int[][] c;

        /**
         * 存储最长回文子序列的路径
         */
        public char[][] b;
    }

    /**
     * 求解最长回文子序列
     * @param x 输入的字符串
     * @return LPSPair
     */
    public static LPSPair solution(char[] x) {
        LPSPair pair = new LPSPair();
        int[][] c = new int[x.length][x.length];
        pair.c = c;

        for (int l = 1; l < x.length; l++) {
            for (int i = 0; i < x.length - l; i++) {
                int j = i + l - 1;
                if (x[i] == x[j]) {
                    if (i == j) {
                        c[i][j] = 1;
                    }
                    else if (i == j-1) {
                        c[i][j] = 2;
                    }
                    else {
                        c[i][j] = c[i+1][j-1] + 2;
                    }
                } else {
                    if (i == j-1) {
                        c[i][j] = 1;
                    }
                    else if (c[i][j-1] >= c[i+1][j]) {
                        c[i][j] = c[i][j-1];
                    }
                    else {
                        c[i][j] = c[i+1][j];
                    }
                }
            }
        }
        return pair;
    }

    /**
     * 打印回文结果
     * x 长度为 12，i = 3, j = 10 则，该方法打印的是基于子字符串 x[i]~x[j] 中的最长回文子序列
     *
     * @param x 输入字符串
     * @param c solution 计算结果
     * @param i x 的子字符串的开头
     * @param j x 的子字符串的结尾
     */
    public static void printLSP(char[] x, int[][] c, int i, int j) {
        if (c[i][j] == 1) {
            System.out.print(x[i] + " ");
            return;
        }
        if (c[i][j] == 2) {
            System.out.print(x[j] + " ");
            printLSP(x, c, i, j-1);
        }
        else if (i < j-1 && x[i] == x[j]) {
            System.out.print(x[i] + " ");
            printLSP(x, c, i+1, j-1);
            System.out.print(x[j] + " ");
        }
        else if (c[i+1][j] > c[i][j-1]) {
            printLSP(x, c, i, j-1);
            System.out.print(x[j] + " ");
        }
        else {
            System.out.print(x[i] + " ");
            printLSP(x, c, i+1, j);
        }
    }
}
