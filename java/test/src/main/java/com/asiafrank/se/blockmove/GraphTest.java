package com.asiafrank.se.blockmove;

/**
 * Created by Xiaofan Zhang on 3/2/2016.
 */
public class GraphTest {

    // 0: 白, 1: 红, 2: 蓝
    // x y轴要一致
    private static int[][] origin = {
            {0, 1, 1, 1},
            {1, 1, 1, 1},
            {2, 2, 2, 2},
            {2, 2, 2, 2}
    };

    private static int[][] temp = {
            {0, 1, 1, 1},
            {1, 1, 1, 1},
            {2, 2, 2, 2},
            {2, 2, 2, 2}
    };

    private static int[][] goal = {
            {1, 1, 1, 1},
            {1, 2, 0, 1},
            {2, 2, 1, 2},
            {2, 2, 2, 2}
    };

    /**
     * 主函数
     * @param args 命令参数
     */
    public static void main(String[] args) {
        System.out.println("before:\n" + matrixString(temp));
        Coordinate base = Coordinate.getInstance(0, 0);
        Coordinate to = Coordinate.getInstance(1, 0);
        move(base, to);
        System.out.println("after:\n" + matrixString(temp));
    }

    /**
     * 移动棋子
     * @param base 棋子当前位置
     * @param to 棋子要移动的位置
     */
    public static void move(Coordinate base, Coordinate to) {
        int x1 = base.getX();
        int y1 = base.getY();

        int x2 = to.getX();
        int y2 = to.getY();

        temp[x1][y1] = temp[x1][y1] ^ temp[x2][y2];
        temp[x2][y2] = temp[x1][y1] ^ temp[x2][y2];
        temp[x1][y1] = temp[x1][y1] ^ temp[x2][y2];
    }

    /**
     * 将数组转换为矩阵字符串
     * @param arr input int array
     * @return
     */
    public static String matrixString(int[][] arr) {
        StringBuilder s = new StringBuilder();
        int xlen = arr.length;
        int ylen = arr[0].length;

        // x和y轴互换位置, 输出的字符串才能正确
        int[][] t = new int[ylen][xlen];
        for (int i0 = 0; i0 < ylen; i0++) {
            for (int j0 = 0; j0 < xlen; j0++) {
                t[i0][j0] = arr[j0][i0];
            }
        }

        for (int i = 0; i < t.length; i++) {
            int len = t[i].length;
            for (int j = 0; j < len; j++) {
                s.append(t[i][j]);
                if (j < (len - 1)) {
                    s.append(" ");
                } else {
                    s.append("\n");
                }
            }
        }
        return s.toString();
    }
}
