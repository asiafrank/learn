package com.asiafrank.labuladong;

/**
 * n 皇后问题
 * 计算 n 皇后有几个解, 不能同行，同列，同斜线
 * @author zhangxiaofan 2021/02/25-10:52
 */
public class NQueens {

    /**
     * n 为几个皇后
     */
    public static int nQueens(int n) {
        // table[x] = y, 代表坐标 (x,y) 放了一个皇后
        int[] table = new int[n];
        return process(table, 0, n);
    }

    /**
     * 递归放 皇后
     * @param table n x n 的表格
     * @param i  现在要放第 i 个皇后，即纵坐标
     * @param n  table 的大小
     * @return 解的个数
     */
    private static int process(int[] table, int i, int n) {
        // base case
        if (i == n) { // 表示放完了，就是一个解
            return 1;
        }

        int rs = 0; // 记录下能有几种解法
        // 横坐标依次放过去, (i,j)
        for (int j = 0; j < n; j++) {
            table[i] = j;
            if (isValid(table, i, n)) { // 判断不同列，不同斜线
                rs += process(table, i + 1, n); // 放下一个皇后
            }
        }
        return rs;
    }

    /**
     * 判断不同列，不同斜线。
     * 因为递归是 i 递增来放皇后的，所以无需判断同行的情况
     *
     * 只需判断 table[i] 与 table[0~i-1] 时的值做对比即可
     *
     * @param table 表格
     * @param i     已经放了几个皇后了
     * @param n     列的规模
     * @return true, 满足不同列，不同斜线；false, 不满足
     */
    private static boolean isValid(int[] table, int i, int n) {
        int x = i;
        int y = table[i];

        for (int x1 = 0; x1 < i; x1++) {
            int y1 = table[x1];
            if (y1 == y) { // 同列
                return false;
            }
            if (Math.abs(x - x1) == Math.abs(y - y1)) { // 同斜线
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
