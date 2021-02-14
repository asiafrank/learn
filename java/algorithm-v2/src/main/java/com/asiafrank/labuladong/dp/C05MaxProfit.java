package com.asiafrank.labuladong.dp;

/**
 * 买卖股票的最佳时机
 *
 * @author zhangxiaofan 2021/02/13-16:31
 */
public class C05MaxProfit {

    /**
     * 121. 买卖股票的最佳时机
     * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/
     * 只允许一次买卖
     *
     * dp[i][0] = x 表示 i 天，未持有股票状态的最大利润为 x
     * dp[i][1] = x 表示 i 天，持有股票状态的最大利润为 x
     *
     * 目标是 dp[prices.length - 1][0]
     *
     * @return 只买卖一次可以获得的最大利润
     */
    public static int maxProfit0(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int len = prices.length;
        int[][] dp = new int[len][2];

        // base case
        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for (int i = 1; i < len; i++) {
            dp[i][0] = Math.max(dp[i - 1][1] + prices[i], dp[i - 1][0]);
            dp[i][1] = Math.max(-prices[i], dp[i - 1][1]);
            // 只允许一次买卖，所以 dp[i][1] = max(dp[i - 1][0] - prices[i], dp[i - 1][1])
            // 中 dp[i - 1][0] 舍去，以为买入时，必定是 0 - prices[i]
        }

        return dp[len - 1][0];
    }

    /**
     * {@link #maxProfit0(int[])} 的常数项优化
     * 只需 dp_i_0 和 dp_i_1 两个变量即可
     */
    public static int maxProfit0_1(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int len = prices.length;

        // base case
        int dp_i_0 = 0;
        int dp_i_1 = -prices[0];

        for (int i = 1; i < len; i++) {
            dp_i_0 = Math.max(dp_i_1 + prices[i], dp_i_0);
            dp_i_1 = Math.max(-prices[i], dp_i_1);
        }

        return dp_i_0;
    }

    //--------------------------------------------------------

    /**
     * 122. 买卖股票的最佳时机 II
     * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/
     * 可以无限次买卖
     *
     * 递推式：
     * dp[i][0] = max(dp[i - 1][1] + prices[i], dp[i - 1][0])
     * dp[i][1] = max(dp[i - 1][0] - prices[i], dp[i - 1][1])
     *
     * 目标：
     * dp[len - 1][0]
     */
    public static int maxProfit1(int[] prices) {
        if (prices == null || prices.length == 0)
            return 0;

        int len = prices.length;
        int[][] dp = new int[len][2];
        // base case
        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for (int i = 1; i < len; i++) {
            dp[i][0] = Math.max(dp[i - 1][1] + prices[i], dp[i - 1][0]);
            dp[i][1] = Math.max(dp[i - 1][0] - prices[i], dp[i - 1][1]);
        }
        return dp[len - 1][0];
    }

    //------------------------------------------------------------

    /**
     * 123. 买卖股票的最佳时机 III
     * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iii/
     * 只允许2次交易。
     * 注意每次交易包含一次买，一次卖
     *
     * dp[i][k][0] 表示 i 天第 k 次交易，不持有股票状态的利润
     * dp[i][k][1] 表示 i 天第 k 次交易，持有股票状态的利润
     *
     * 递推式：
     * dp[i][k][0] = max(dp[i - 1][k][1] + prices[i], dp[i - 1][k][0])
     * dp[i][k][1] = max(dp[i - 1][k - 1][0] - prices[i], dp[i - 1][k][1])
     *
     * 目标：
     * dp[len - 1][2][0]
     */
    public static int maxProfit2(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int k = 2; // 最多交易几次
        int len = prices.length;
        int[][][] dp = new int[len][k + 1][2];

        // base case, k = 0 为交易 0 次
        dp[0][0][0] = 0;           // 第0天不买入, 本质就是假设还有 2 次买入卖出机会
        dp[0][0][1] = 0;           // 第0天持有，本质就是假设还有 2 次买入卖出机会
        dp[0][1][0] = 0;           // 第0天第1次交易卖出, 本质就是假设还有 1 次买入卖出机会
        dp[0][1][1] = - prices[0]; // 第0天第1次交易买入, 本质就是假设还有 1 次卖出 和 1次买入卖出机会
        dp[0][2][0] = 0;           // 第0天第2次交易卖出, 由于之前没有买入，所以是 0, 本质就是假设只有1次卖出机会
        dp[0][2][1] = - prices[0]; // 第0天第2次交易买入, 本质就是假设只有1次卖出机会

        for (int i = 1; i < len; i++) {
            for (int j = 1; j <= k; j++) {
                dp[i][j][0] = Math.max(dp[i - 1][j][1] + prices[i], dp[i - 1][j][0]);
                dp[i][j][1] = Math.max(dp[i - 1][j - 1][0] - prices[i], dp[i - 1][j][1]);
            }
        }
        return dp[len - 1][2][0];
    }

    /**
     * 188. 买卖股票的最佳时机 IV
     * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iv/
     * 最多交易 k 次
     */
    public static int maxProfitK(int[] prices, int k) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int len = prices.length;
        int[][][] dp = new int[len][k + 1][2];

        // base case, k = 0 为交易 0 次
        dp[0][0][0] = 0; // 第0天不买入
        for (int j = 1; j <= k; j++) {
            dp[0][j][1] = - prices[0]; // 第0天第j次交易买入, 也就是还剩余 k-j 次买入卖出机会
        }

        for (int i = 1; i < len; i++) {
            for (int j = 1; j <= k; j++) {
                dp[i][j][0] = Math.max(dp[i - 1][j][1] + prices[i], dp[i - 1][j][0]);
                dp[i][j][1] = Math.max(dp[i - 1][j - 1][0] - prices[i], dp[i - 1][j][1]);
            }
        }
        return dp[len - 1][k][0];
    }

    /**
     * 309. 最佳买卖股票时机含冷冻期
     * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/
     * 无限次买卖
     * 拿 {@link #maxProfit1(int[])} 来改
     *
     *
     * 递推式：
     * dp[i][0] = max(dp[i - 1][1] + prices[i], dp[i - 1][0])
     * dp[i][1] = max(dp[i - 2][0] - prices[i], dp[i - 1][1])
     *
     * 注：冷冻期的含义是：卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
     *    由于冷冻期为 1天，所以 dp[i][1] 由状态 dp[i-2][0] 转换而来
     *
     * 目标：
     * dp[len - 1][0]
     */
    public static int maxProfitWithCoolDown(int[] prices) {
        if (prices == null || prices.length < 2)
            return 0;

        int len = prices.length;
        int[][] dp = new int[len][2];
        // base case
        dp[0][0] = 0; // 第 0 天，股票卖出，得到的利润是 0
        dp[0][1] = -prices[0]; // 第 0 天，股票买入，得到的利润是 -prices[0]
        dp[1][0] = Math.max(dp[0][1] + prices[1], dp[0][0]); // 第 1 天，求最大
        dp[1][1] = Math.max(-prices[1], dp[0][1]); // 第 1 天，股票买入或者前一天买入取最大

        for (int i = 2; i < len; i++) {
            dp[i][0] = Math.max(dp[i - 1][1] + prices[i], dp[i - 1][0]);
            dp[i][1] = Math.max(dp[i - 2][0] - prices[i], dp[i - 1][1]);
        }

        return dp[len - 1][0];
    }

    /**
     * 714. 买卖股票的最佳时机含手续费
     * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/
     * 股票买卖手续费，无限次交易
     * 手续费只在卖出的时候扣除
     *
     * 拿 {@link #maxProfit1(int[])} 改
     *
     * 递推式：
     * dp[i][0] = max(dp[i - 1][1] + prices[i] - fee, dp[i - 1][0])
     * dp[i][1] = max(dp[i - 1][0] - prices[i], dp[i - 1][1])
     *
     * 目标：
     * dp[len - 1][0]
     */
    public static int maxProfitWithFee(int[] prices, int fee) {
        if (prices == null || prices.length == 0)
            return 0;

        int len = prices.length;
        int[][] dp = new int[len][2];
        // base case
        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for (int i = 1; i < len; i++) {
            dp[i][0] = Math.max(dp[i - 1][1] + prices[i] - fee, dp[i - 1][0]);
            dp[i][1] = Math.max(dp[i - 1][0] - prices[i], dp[i - 1][1]);
        }
        return dp[len - 1][0];
    }

    public static void main(String[] args) {
//        testMaxProfit0();
//        testMaxProfit1();
//        testMaxProfit2();
//        testMaxProfitK();
//        testMaxProfitWithCoolDown();
        testMaxProfitWithFee();
    }

    /**
     * 只允许一次买卖的
     */
    private static void testMaxProfit0() {
        int[] prices = new int[]{7,1,5,3,6,4};
        int m = maxProfit0_1(prices); // 5
        System.out.println(m);

        prices = new int[]{7,6,4,3,1};
        m = maxProfit0_1(prices); // 0
        System.out.println(m);
    }

    /**
     * 允许无数次买卖的
     */
    private static void testMaxProfit1() {
        int[] prices = new int[]{7,1,5,3,6,4};
        int m = maxProfit1(prices); // 7
        System.out.println(m);

        prices = new int[]{1,2,3,4,5};
        m = maxProfit1(prices); // 4
        System.out.println(m);

        prices = new int[]{7,6,4,3,1};
        m = maxProfit1(prices); // 0
        System.out.println(m);
    }

    /**
     * 只允许2次买卖
     */
    private static void testMaxProfit2() {
        int[] prices = new int[]{3,3,5,0,0,3,1,4};
        int m = maxProfit2(prices); // 6
        System.out.println(m);

        prices = new int[]{1,2,3,4,5};
        m = maxProfit2(prices); // 4
        System.out.println(m);

        prices = new int[]{7,6,4,3,1};
        m = maxProfit2(prices); // 0
        System.out.println(m);

        prices = new int[]{1};
        m = maxProfit2(prices); // 0
        System.out.println(m);
    }

    /**
     * 最多交易 k 次
     */
    private static void testMaxProfitK() {
        int[] prices = new int[]{2,4,1};
        int m = maxProfitK(prices, 2); // 2
        System.out.println(m);

        prices = new int[]{3,2,6,5,0,3};
        m = maxProfitK(prices, 2); // 7
        System.out.println(m);

        prices = new int[]{1};
        m = maxProfitK(prices, 1); // 0
        System.out.println(m);
    }

    /**
     * 冷冻期以及无限次交易
     */
    private static void testMaxProfitWithCoolDown() {
        int[] prices = new int[]{1,2,3,0,2};
        int m = maxProfitWithCoolDown(prices); // 3
        System.out.println(m);

        prices = new int[]{1};
        m = maxProfitWithCoolDown(prices); // 0
        System.out.println(m);

        prices = new int[]{1,2};
        m = maxProfitWithCoolDown(prices); // 1
        System.out.println(m);

        prices = new int[]{1,2,4};
        m = maxProfitWithCoolDown(prices); // 3
        System.out.println(m);
    }

    private static void testMaxProfitWithFee() {
        int[] prices = new int[]{1, 3, 2, 8, 4, 9};
        int m = maxProfitWithFee(prices, 2); // 8
        System.out.println(m);
    }
}
