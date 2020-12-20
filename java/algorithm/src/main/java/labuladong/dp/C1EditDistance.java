package labuladong.dp;

/**
 * 编辑距离
 *
 * 给定两个字符串 s1 和 s2，计算出 s1 转换成 s2 的最小操作数。
 * 你可以对字符串做以下操作：
 * 1. 插入一个字符
 * 2. 删除一个字符
 * 3. 替换一个字符
 *
 * 例子：
 * s1 = "horse", s2 = "ros"
 * 输出：3
 * horse -> rorse (替换 h -> r)
 * rorse -> rose (删除 r)
 * rose -> ros (删除 e)
 * @author zhangxiaofan 2020/12/18-11:30
 */
public class C1EditDistance {

    /**
     * 将 s1 转成 s2 的最小编辑次数是多少
     * 递归方法
     *
     * @param s1 源字符出
     * @param s2 目标字符串
     * @return 最小编辑距离
     */
    public static int minDistance(String s1, String s2) {
        if (s1 == null || s2 == null)
            return 0;

        char[] s1Array = s1.toCharArray();
        char[] s2Array = s2.toCharArray();
        return process(s1Array, s1Array.length - 1, s2Array, s2Array.length - 1);
    }

    /**
     * 将 s1 转成 s2 的最小编辑次数是多少
     * 从后往前比较，递归调用最后缩小子问题
     *
     * @param s1 源字符串数组
     * @param i 当前需要比较的 s1 元素下标
     * @param s2 目标字符串数组
     * @param j  当前需要比较的 s2 元素下标
     * @return 最小编辑距离
     */
    private static int process(char[] s1, int i, char[] s2, int j) {
        if (i < 0)
            return j + 1; // s2 还剩 j + 1 个字符需要编辑(插入)，（包含 0 下标的字符所以 + 1）
        if (j < 0)
            return i + 1; // s1 还剩 i + 1 个字符需要编辑(删除)，（包含 0 下标的字符所以 + 1）

        if (s1[i] == s2[j]) {
            return process(s1, i - 1, s2, j - 1); // skip
        } else {
            // 1. 插入一个字符, 相当于 i 不动，j - 1
            int p1 = process(s1, i, s2, j - 1) + 1;
            // 2. 删除一个字符，相当于 i - 1, j 不动 (s1[i] 字符被删了)
            int p2 = process(s1, i - 1, s2, j) + 1;
            // 3. 替换一个字符，相当于 i - 1, j - 1
            int p3 = process(s1, i - 1, s2, j - 1) + 1;
            return Math.min(p1, Math.min(p2, p3));
        }
    }

    // TODO: dp自底向上求解

    public static void main(String[] args) {
        String s1 = "horse";
        String s2 = "ros";
        int d = minDistance(s1, s2);
        System.out.println(d);

        s1 = "intention";
        s2 = "execution";
        d = minDistance(s1, s2);
        System.out.println(d);
    }
}
