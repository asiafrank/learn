package coursex;

/**
 * 假设有排成一行的N个位置，记为1~N，N 一定大于或等于 2
 * 开始时机器人在其中的M位置上(M 一定是 1~N 中的一个)
 * 如果机器人来到1位置，那么下一步只能往右来到2位置；
 * 如果机器人来到N位置，那么下一步只能往左来到 N-1 位置；
 * 如果机器人来到中间位置，那么下一步可以往左走或者往右走；
 * 规定机器人必须走 K 步，最终能来到P位置(P也是1~N中的一个)的方法有多少种
 * 给定四个参数 N、M、K、P，返回方法数。
 * 1.用暴力递归解
 * 2.重复子问题结果保存下来
 */
public class C14RobotWalk {

    /**
     * 暴力递归方法
     * O(2^k)
     *
     * @param n  n 个位置
     * @param m  机器人起始位置 m
     * @param k  走 k 步
     * @param p  目的地 p
     * @return 有几种方法
     */
    public static int countStepMethods(int n, int m, int k, int p) {
        if (k == 0) {
            if (m == p) // k 步走完时，走到了 p，则方法数 + 1
                return 1;
            else
                return 0;
        }

        int c = 0;
        if (m == 1) { // 1 位置，只能往右走
            c += countStepMethods(n, m + 1, k - 1, p);
        } else if (m == n) { // n 位置，只能往左走
            c += countStepMethods(n, m - 1, k - 1, p);
        } else {
            c += countStepMethods(n, m - 1, k - 1, p); // 往左走
            c += countStepMethods(n, m + 1, k - 1, p); // 往右走
        }
        return c;
    }

    // 记忆法省略

    // TODO: 动态规划做法

    public static void main(String[] args) {
        int c = countStepMethods(7, 3, 3, 2); // 3 种方法
        System.out.println(c);
    }
}
