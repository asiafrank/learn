package coursex.ad;

import com.asiafrank.util.Printer;

/**
 * 在一个无序数组中，求第 k 小的数。
 *
 * 解法一：
 * 荷兰国旗问题相似。
 * 递归方法：process(arr, 0, n - 1, k - 1)
 * 在 arr[0..n-1] 范围上找位于 k 位置的数
 *
 * 收敛于 O(n)
 *
 * 解法二：
 * bfprt
 * O(n)
 *
 * 1. 随机选一个数 m。（选择有讲究）
 *    a.五个数一组
 *    b.每组排序
 *    c.每组中位数组成数组 m
 *    d.再用m数组中，递归调用 bfprt(arr, k)
 * 2. 荷兰国旗问题, 小于m，等于 m，大于 m 部分
 * 3. 看等于的部分有没有命中 k，如果命中则返回；
 *    如果不命中，则左或右部分继续 partition。
 *
 *
 */
public class C4Bfprt {

    // 解法一

    /**
     * 用随机选数 + partition 找第k小的数
     * @param arr 数组
     * @param k 第 k 个数(从 0 开始)
     * @return -1, 没找到；第 k 小的数
     */
    public static int findKthMin(int[] arr, int k) {
        if (arr == null || arr.length == 0)
            return -1;

        if (k >= arr.length)
            return -1;

        return process1(arr, 0, arr.length - 1, k);
    }

    /**
     * 随机选数 + partition
     * @param arr 数组
     * @param l   要处理的左边界
     * @param r   要处理的右边界
     * @param k   找第 k 小的数
     * @return 第 k 小的数; -1 没找到
     */
    private static int process1(int[] arr, int l, int r, int k) {
        if (l > r) {
            return -1;
        }

        int pivotIndex = l + (int)(Math.random() * (r - l + 1));
        int pivot = arr[pivotIndex];

        int[] range = partition(arr, l, r, pivot);
        int left = range[0], right = range[1];
        if (left <= k && right >= k) { // = pivot 部分包含 k，则 arr[left] 就是第 k 小的数
            return arr[left];
        } else if (k < left) {
            // 从 arr[l..left - 1] 部分找
            return process1(arr, l, left - 1, k);
        } else if (k > right) {
            // 从 arr[right + 1, r] 部分找
            return process1(arr, right + 1, r, k);
        }
        return -1;
    }

    /**
     * 以 pivot 为基准，将 arr[l..r] 划分为 < pivot, = pivot, >pivot 三个部分
     * 返回 '= pivot' 部分的左右边界
     * @param arr 数组
     * @param l   左边界
     * @param r   右边界
     * @param pivot 选出的数
     * @return 长度为2的数组,[0] 元素是 '= pivot' 的左边界，[1] 为右边界
     */
    private static int[] partition(int[] arr, int l, int r, int pivot) {
        int left = l - 1; // < 的部分右边界
        int right = r + 1; // > 的部分左边界

        int i = l;
        while (i < right) {
            if (arr[i] > pivot) {
                right--;
                swap(arr, i, right);
            } else if (arr[i] < pivot) {
                left++;
                i++;
            } else if (arr[i] == pivot){
                i++;
            }
        }
        return new int[] {left + 1, right - 1}; // left + 1: '= pivot' 的左边界；right + 1: '= pivot' 的右边界
    }

    private static void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    // 解法二：bfprt

    public static void main(String[] args) {
        int[] arr = new int[] {2, 4, 5, 6, 3, 9, 3};
        int rs = findKthMin(arr, 2);
        System.out.println(rs);
    }
}
