package coursex;

/**
 * 汉诺塔问题
 */
public class Hanoi {

    // 暴力解, 递归
    public static void hanoi(int n) {
        if (n > 0) {
            move(n, "left", "right", "mid");
        }
    }

    /**
     * 目的：将 n 从 from 移到 to
     * 1.将 1~(n-1) 从 from 移到 other
     * 2.将 n 从 from 移到 to
     * 3.将 1~(n-1) 从 other 移到 to
     */
    private static void move(int n, String from, String to, String other) {
        if (n == 1) {
            System.out.println("move 1: " + from + "-->" + to);
            return;
        }

        // 1.
        move(n - 1, from, other, to);
        // 2.
        System.out.println("move " + n + ": " + from + "-->" + to);
        // 3.
        move(n - 1, other, to , from);
    }

    public static void main(String[] args) {
        hanoi(3);
    }
}
