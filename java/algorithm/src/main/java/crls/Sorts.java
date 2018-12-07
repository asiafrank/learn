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

    //------------------------
    // 归并排序
    //------------------------

    /**
     * 归并排序
     * 给定一个数组
     *  索引：   0   1   2   3   4   5   6
     *         ---------------------------
     *  数组： | 7 | 6 | 5 | 4 | 3 | 2 | 1 |
     *         ---------------------------
     * @param a  需要归并的数组
     * @param lo a数组里，需要排序的范围开始下标
     * @param hi a数组里，需要排序的范围结束下标
     */
    public static void mergeSort(int[] a, int lo, int hi) {
        if (lo >= hi) return;

        int mid = lo + (hi - lo) / 2;
        mergeSort(a, lo, mid);
        mergeSort(a, mid + 1, hi);

        merge(a, lo, mid, hi);
    }

    /**
     * 合并方法
     *
     *  索引：   0   1   2   3   4   5   6
     *         ---------------------------
     *  数组： | 6 | 7 | 4 | 5 | 3 | 2 | 1 |
     *         ---------------------------
     *          lo  mi mi+1 hi
     *          k
     *  n1 = mi - lo + 1   [6,7]
     *  n2 = hi - mi       [4,5]
     *  新建两个新数组 left，right
     *  left: [6,7,INT_MAX]
     *  right: [4,5, INT_MAX]
     *  k 从 lo 开始, 到 hi 结束，代表对比的结果插入到 k 位置处。
     *  还剩最后一个数未比较时，和 INT_MAX 比较，小的那个赋值给 a[k]
     *  且这时 k = hi，最后一个值处理完毕，退出循环
     *
     * @param a   需要归并的数组
     * @param lo  a数组里，排好序的第一个数组范围开始下标
     * @param mid a数组里，排好序的第一个数组范围的结束下标
     * @param hi  a数组里，排好序的第二个数组范围的结束下标
     */
    private static void merge(int[] a, int lo, int mid, int hi) {
        int n1 = mid - lo + 1;
        int n2 = hi - mid;

        int[] left = new int[n1 + 1];
        int[] right = new int[n2 + 1];

        for (int i = 0; i < n1; i++)
            left[i] = a[lo + i];
        for (int j = 0; j < n2; j++)
            right[j] = a[mid + 1 + j];

        left[n1]  = Integer.MAX_VALUE;
        right[n2] = Integer.MAX_VALUE;

        for (int k = lo, i = 0, j = 0; k <= hi; k++) {
            int l = left[i];
            int r = right[j];

            if (l <= r) {
                a[k] = l;
                i++;
            } else {
                a[k] = r;
                j++;
            }
        }
    }
}
