package labuladong.dp;

/**
 * 你⾯前有⼀栋从 1 到 N 共 N 层的楼，然后给你 K 个鸡蛋 （ K ⾄少为 1）。
 * 现在确定这栋楼存在楼层 0 <= F <= N，在这层楼将鸡蛋扔下去，
 * 鸡蛋恰好没摔碎（⾼于 F 的楼层都会碎，低于 F 的楼层都不会碎）。
 * 现在问你，最坏情况下，你⾄少要扔⼏次鸡蛋，才能确定这个楼层 F 呢？
 *
 * 什么是【最坏情况】?
 * 鸡蛋一直没碎，则一直尝试下去。
 * 鸡蛋立马碎了，则 K 个鸡蛋每次都耗尽。
 * <pre>
 * // i 为当前层
 * c = max(
 *    dp(k, n - i), 一直不碎尝试上面的楼层
 *    dp(k - 1, i - 1) 每次都碎尝试下面的楼层
 * ) + 1;
 * </pre>
 *
 * 什么是【至少】？
 * 如果鸡蛋没限制，也就是鸡蛋一直没碎的情况下，则二分查找就是最少的尝试次数。
 * 如果鸡蛋立马碎了，则从 0 开始尝试就碎了，就是最少尝试次数。
 * 也就是说，每层楼都试下，看碎没碎，取最小的尝试次数。
 * <pre>
 * for (int i = 1; i < n + 1; i++) {
 *     c = 碎了与没碎尝试
 *     count = min(count, c); // 当前尝试 c 与 i-1 层尝试 count 取最小值
 * }
 * </pre>
 *
 * @author zhangxiaofan 2020/12/21-14:05
 */
public class EggDrop {

    /**
     * 扔鸡蛋
     * 递归穷举，每层楼都尝试碎没碎，碎了往下，没碎往上
     * 时间复杂度：O(k*n^2)
     * 空间复杂度：O(kn)
     *
     * @param k k 个鸡蛋
     * @param n n 层楼扔鸡蛋
     * @return 最坏情况下，最少尝试次数
     */
    public static int eggDrop(int k, int n) {
        // 只剩一个鸡蛋了，只能从 1~n 尝试。最坏情况下，鸡蛋不会碎，尝试了 n 次
        if (k == 1)
            return n;
        if (n <= 0)
            return 0;

        // 层数从1开始, 每层都尝试扔; 如果从 0 层开始，会 n - 0 = n 会递归错误
        int count = Integer.MAX_VALUE;
        for (int i = 1; i < n + 1; i++) {
            int c = Math.max(
                eggDrop(k, n - i),    // 没碎，往上尝试
                eggDrop(k - 1, i - 1) // 碎了，往下尝试
            ) + 1; // +1 是算上当前楼层扔的次数
            count = Math.min(count, c); // 至少扔的次数
        }
        return count;
    }

    // ------ 记忆法 -------

    public static int eggDropMemo(int k, int n) {
        int[][] memo = new int[k + 1][n + 1];
        // 初始化 -1；
        for (int i = 0; i < k + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                memo[i][j] = -1;
            }
        }
        int c = doEggDropMemo(k, n, memo);
//        Printer.print2DArray(memo);
        return c;
    }

    private static int doEggDropMemo(int k, int n, int[][] memo) {
        // 只剩一个鸡蛋了，只能从 1~n 尝试。最坏情况下，鸡蛋不会碎，尝试了 n 次
        if (k == 1) {
            memo[k][n] = n;
            return n;
        }
        if (n <= 0) {
            memo[k][n] = 0;
            return 0;
        }

        if (memo[k][n] != -1) {
            return memo[k][n];
        }

        // 层数从1开始, 每层都尝试扔; 如果从 0 层开始，会 n - 0 = n 会递归错误
        int count = Integer.MAX_VALUE;
        for (int i = 1; i < n + 1; i++) {
            int c = Math.max(
                    doEggDropMemo(k, n - i, memo),    // 没碎，往上尝试
                    doEggDropMemo(k - 1, i - 1, memo) // 碎了，往下尝试
            ) + 1; // +1 是算上当前楼层扔的次数
            count = Math.min(count, c); // 至少扔的次数
        }

        memo[k][n] = count;
        return count;
    }

    //-------- 二分优化 -------

    /**
     * 根据 eggDrop(k, n) 的定义：k 个鸡蛋，n 层楼，最坏情况下，至少尝试几次。
     * 其中 for 循环中
     * i 递增，eggDrop(k, n - i) 单调递减
     *        eggDrop(k - 1, i - 1) 单调递增
     * 求最小值 min(count, c) 就相当于上面两个函数的交点。
     * 所以可以用二分法来求交点
     */
    public static int eggDropBinarySearch(int k, int n) {
        int[][] memo = new int[k + 1][n + 1];
        // 初始化 -1；
        for (int i = 0; i < k + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                memo[i][j] = -1;
            }
        }
        int c = doEggDropBinarySearch(k, n, memo);
        return c;
    }

    private static int doEggDropBinarySearch(int k, int n, int[][] memo) {
        // 只剩一个鸡蛋了，只能从 1~n 尝试。最坏情况下，鸡蛋不会碎，尝试了 n 次
        if (k == 1) {
            memo[k][n] = n;
            return n;
        }
        if (n <= 0) {
            memo[k][n] = 0;
            return 0;
        }

        if (memo[k][n] != -1) {
            return memo[k][n];
        }

        // 层数从1开始, 每层都尝试扔; 如果从 0 层开始，会 n - 0 = n 会递归错误
        int count = Integer.MAX_VALUE;
        int lo = 1, hi = n;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            int no_broken = doEggDropBinarySearch(k, n - mid, memo);  // 单调递减
            int broken = doEggDropBinarySearch(k - 1, mid - 1, memo); // 单调递增
            if (no_broken > broken) { // mid 往大的地方去
                lo = mid + 1;
                count = Math.min(count, no_broken + 1);
            } else { // mid 往小的地方去
                hi = mid - 1;
                count = Math.min(count, broken + 1);
            }
        }

        memo[k][n] = count;
        return count;
    }

    public static void main(String[] args) {
        int c = eggDrop(2, 10);
        System.out.println(c);

        c = eggDropMemo(2, 10);
        System.out.println(c);

        c = eggDropBinarySearch(2, 10);
        System.out.println(c);
    }
}
