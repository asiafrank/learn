package crls.greedy;

/**
 * P244 习题 16.2-2 0-1背包问题
 *
 * @author zhangxf created at 1/30/2019.
 */
public class Dynamic01Knapsack {
    public static class D01KnapsackPair {
        public int[][] r;
        public char[][] b;
    }

    /**
     * 求解 01 背包问题
     *
     * 下标从1开始
     * @param w    从小到大排列的商品重量列表
     * @param v    w 数组对应的价值列表
     * @param maxW 背包可容纳的最大重量
     * @return pair
     */
    public static D01KnapsackPair solution(int[] w, int[] v, int maxW) {
        D01KnapsackPair pair = new D01KnapsackPair();
        int[][] r = new int[w.length][maxW + 1];
        char[][] b = new char[w.length][maxW + 1];
        pair.r = r;
        pair.b = b;

        for (int i = 1; i < w.length; i++) {
            for (int j = 1; j <= maxW; j++) {
                if (w[i] > j) {
                    r[i][j] = r[i-1][j];
                    b[i][j] = '↑';
                }
                else {
                    int q = r[i-1][j-w[i]] + v[i];
                    if (q > r[i-1][j]) {
                        r[i][j] = q;
                        b[i][j] = '↖';
                    }
                    else {
                        r[i][j] = r[i-1][j];
                        b[i][j] = '↑';
                    }
                }
            }
        }
        return pair;
    }

    public static void printD01Knapsack(char[][] b, int[] w, int i, int j) {
        if (i <= 0 || j <= 0)
            return;
        if (b[i][j] == '↖') {
            System.out.print(i + " ");
            printD01Knapsack(b, w, i-1, j-w[i]);
        } else {
            printD01Knapsack(b, w, i-1, j);
        }
    }
}
