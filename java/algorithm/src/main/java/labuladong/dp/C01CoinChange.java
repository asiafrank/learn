package labuladong.dp;

/**
 * 凑硬币
 * 给你 k 种⾯值的硬币，⾯值分别为 c1, c2 ... ck ，
 * 每种硬币的数量⽆限，再给⼀个总⾦额 amount ，
 * 问你最少需要⼏枚硬币凑出这个⾦额，如果不可能凑出，算法返回 -1 。
 *
 * @author zhangxiaofan 2020/12/15-10:08
 */
public class C01CoinChange {
    /**
     * coins 中是可选硬币面值，amount 是目标金额
     * 暴力递归
     */
    static int coinChange(int[] coins, int amount) {
        if (amount == 0)
            return 0;
        if (amount < 0)
            return -1;

        int c = Integer.MAX_VALUE;
        for (int i = 0; i < coins.length; i++) {
            int r = coinChange(coins, amount - coins[i]);
            if (r < 0) {
                continue;
            }
            c = Math.min(r + 1, c);
        }
        return c == Integer.MAX_VALUE ? -1 : c;
    }

    /**
     * dp 表
     * dp[i] = c; 含义是 amount = i 时，最小的凑硬币数量为 c
     */
    static int coinChangeDp(int[] coins, int amount) {
        if (amount == 0)
            return 0;
        if (amount < 0)
            return -1;

        int[] dp = new int[amount + 1];

        // 初始化为 MAX_VALUE
        dp[0] = 0; // base case
        for (int i = 1; i < dp.length; i++) {
            dp[i] = Integer.MAX_VALUE;
        }

        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i - coin < 0) {
                    continue;
                }
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
        return dp[amount];
    }

    public static void main(String[] args) {
        int[] coins = new int[] {1, 2, 5};

        for (int amount = 0; amount < 20; amount++) {
            int c0 = coinChange(coins, amount);
            int c1 = coinChangeDp(coins, amount);
            System.out.println(amount + ": " + (c0 == c1) + ":" + c0);
        }
    }
}
