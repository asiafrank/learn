package coursex;

/**
 * 范围上尝试的模型
 *
 * N皇后
 * N皇后问题是指在N*N的棋盘上要摆N个皇后，
 * 要求任何两个皇后不同行、不同列， 也不在同一条斜线上
 * 给定一个整数n，返回n皇后的摆法有多少种。  n=1，返回1
 * n=2或3，2皇后和3皇后问题无论怎么摆都不行，返回0
 * n=8，返回92
 * (位运算优化解法)
 */
public class C14NQueens {

    /**
     * N皇后问题
     * @param n 问题规模，表示几皇后
     * @return 返回N皇后问题解的个数
     */
    public static int nQueens(int n) {
        // 坐标定义 table[i] = m 代表第 i 行的 m 列
        int[] table = new int[n];
        return process(table, 0, n);
    }

    /**
     * 当前第 i 行，尝试放皇后，得到的解个数
     * @param table 棋盘
     * @param i     现在是放第几行的皇后
     * @param n     有多少列可以放
     * @return 解的个数
     */
    private static int process(int[] table, int i, int n) {
        if (i == n) // 前面都放成功了，就是一个解
            return 1;

        // 在 i 行放皇后，n 个列，都放下试试
        int rs = 0;
        for (int j = 0; j < n; j++) {
            table[i] = j; // i 行，j 列放上皇后
            if (isValid(table, i)) {
                rs += process(table, i + 1, n); // 尝试放下一行
            }
        }
        return rs;
    }

    /**
     * 验证 table 里 从 0 到 i 行放的皇后，不同行、不同列， 也不在同一条斜线上
     * @param table 棋盘
     * @param i     目前放到 i 行
     * @return false, 同行，同列，同斜线的皇后
     */
    private static boolean isValid(int[] table, int i) {
        int m = table[i]; // (i,m) 是当前皇后放置的坐标

        // 当前皇后放置的坐标和前面所有放置的坐标对比
        for (int j = 0; j < i; j++) {
            int k = table[j]; // (j,k)
            if (k == m) // 同列
                return false;
            // 由于递归方法 i 行是递增的，所以不会同行
            // 下面只需判断是否同斜线即可
            // 通过斜率判断是否同斜线： |i - j| == |m - k| 时，表示同斜线
            if (Math.abs(i - j) == Math.abs(m - k)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(nQueens(4));
        System.out.println(nQueens(8));
    }
}
