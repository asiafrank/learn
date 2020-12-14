package coursex;

/**
 * 汉诺塔问题
 */
public class C12Hanoi {

    public static void recursiveMove(int n, String from, String to, String other) {
        if (n == 1) {
            System.out.println("Move 1 from " + from + " to " + to);
            return;
        }

        recursiveMove(n - 1, from, other, to);
        System.out.println("Move " + n + " from " + from + " to " + to);
        recursiveMove(n - 1, other, to, from);
    }

    /**
     * 汉诺塔移动步骤数量推导：
     * F(n) = 2F(n-1) + 1
     * F(1) = 1
     * F(2) = 2 * 1 + 1 = 3
     * F(3) = 2 * 3 + 1 = 7
     * ...
     * F(n) = 2 ^ n - 1
     */
    public static long hanoiCount(int n) {
        long f_n = 1;
        for (int i = 2; i <= n; i++) {
            f_n = 2 * f_n + 1;
        }
        return f_n;
    }

    public static void main(String[] args) {
        recursiveMove(3, "左", "右", "中");
        System.out.println("F(64)=" + hanoiCount(64));
    }
}
