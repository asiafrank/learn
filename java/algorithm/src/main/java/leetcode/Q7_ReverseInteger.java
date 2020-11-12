package leetcode;

/**
 * 整数反转
 * easy
 * https://leetcode-cn.com/problems/reverse-integer/
 */
public class Q7_ReverseInteger {
    public int reverse(int x) {
        boolean neg = ((x >>> 31) & 1) == 1; // 判断是否是负数
        x = neg ? x : -x; // 因为负数值域比正数大一个，所以先转成负数再计算
        int m = Integer.MIN_VALUE / 10; // 限制条件，避免溢出
        int o = Integer.MIN_VALUE % 10; // 限制条件，避免溢出

        int res = 0;
        while (x != 0) {
            if (res < m || (res == m && x % 10 < o)) {
                return 0;
            }

            res = res * 10 + (x % 10);
            x = x / 10;
        }
        return neg ? res : Math.abs(res); // 还原
    }
}
