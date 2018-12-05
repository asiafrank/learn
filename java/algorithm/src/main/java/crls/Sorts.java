package crls;

/**
 * 排序算法
 *
 * @author zhangxf created at 12/5/2018.
 */
public final class Sorts {

    /**
     * 插入排序
     *
     * 给定一个数组
     *  索引：   0   1   2   3   4   5   6
     *         ---------------------------
     *  数组： | 7 | 6 | 5 | 4 | 3 | 2 | 1 |
     *         ---------------------------
     *          i    j
     *  设 0~(j-1) 为已排序的部分，j~n 为未排序部分
     *     i 为已排序部分的游标，从已排序部分的末尾开始向前推移，找到可以插入的索引。
     *
     * @param a length 大于等于 2 的数组
     * @return 从小到大排好序的数组
     */
    public static int[] insertSort(int[] a) {
        if (a == null || a.length <= 1) return a;

        for (int j = 1; j < a.length; j++) {  // j 从 1 开始
            int key = a[j];                   // 暂时保存 j 上的值，因为下面 i 向前推移时，j 上的值要被覆盖
            int i = j - 1;                    // i 从 j-1 开始，即已排序部分的最末尾
            while (i >= 0 && key < a[i]) {    // a[j] 上的值（也就是 key）小于 a[i]，
                a[i+1] = a[i];                // 则将 a[i] 值向右移（为了空出一格，用于 key 的插入）
                i--;                          // i 向左移动一格，用来比较下一个元素, 当 i < 0 时，退出循环
            }
            a[i+1] = key;                     // 因为 while 循环体内退出循环时，多减了 1，所以这里需要 +1 代表可以插入的格
        }
        return a;
    }
}
