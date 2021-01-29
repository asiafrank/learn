package coursex.ad;

import com.asiafrank.util.Printer;

import java.util.Comparator;
import java.util.PriorityQueue;

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

        int[] range = partition0(arr, l, r, pivot);
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
    private static int[] partition0(int[] arr, int l, int r, int pivot) {
        int left = l - 1; // < 的部分右边界
        int right = r + 1; // > 的部分左边界

        int i = l;
        while (i < right) {
            if (arr[i] > pivot) { // [i] > pivot, 则 [i] 与 > 区域左一个交换，i 不变, >区域左扩1
                right--;
                swap(arr, i, right);
            } else if (arr[i] < pivot) { // [i] < pivot, 则 [i] 与 <区域 右一个交换，i++，<区域右扩1
                left++;
                swap(arr, i, left);
                i++;
            } else if (arr[i] == pivot){ // [i] == pivot, 则 i++
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

    // -------------------------
    // 解法二：bfprt


    public static class MaxHeapComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }

    // 利用大根堆，时间复杂度O(N*logK)
    public static int minKth1(int[] arr, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new MaxHeapComparator());
        for (int i = 0; i < k; i++) {
            maxHeap.add(arr[i]);
        }
        for (int i = k; i < arr.length; i++) {
            if (arr[i] < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.add(arr[i]);
            }
        }
        return maxHeap.peek();
    }

    // 改写快排，时间复杂度O(N)
    public static int minKth2(int[] array, int k) {
        int[] arr = copyArray(array);
        return process2(arr, 0, arr.length - 1, k - 1);
    }

    public static int[] copyArray(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i != ans.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    public static int process2(int[] arr, int L, int R, int index) {
        if (L == R) {
            return arr[L];
        }
        int pivot = arr[L + (int) (Math.random() * (R - L + 1))];
        int[] range = partition(arr, L, R, pivot);
        if (index >= range[0] && index <= range[1]) {
            return arr[index];
        } else if (index < range[0]) {
            return process2(arr, L, range[0] - 1, index);
        } else {
            return process2(arr, range[1] + 1, R, index);
        }
    }

    public static int[] partition(int[] arr, int L, int R, int pivot) {
        int less = L - 1;
        int more = R + 1;
        int cur = L;
        while (cur < more) {
            if (arr[cur] < pivot) {
                swap(arr, ++less, cur++);
            } else if (arr[cur] > pivot) {
                swap(arr, cur, --more);
            } else {
                cur++;
            }
        }
        return new int[] { less + 1, more - 1 };
    }

    // 利用bfprt算法，时间复杂度O(N)
    public static int minKth3(int[] array, int k) {
        int[] arr = copyArray(array);
        return bfprt(arr, 0, arr.length - 1, k - 1);
    }

    public static int bfprt(int[] arr, int L, int R, int index) {
        if (L == R) {
            return arr[L];
        }
        int pivot = medianOfMedians(arr, L, R);
        int[] range = partition(arr, L, R, pivot);
        if (index >= range[0] && index <= range[1]) {
            return arr[index];
        } else if (index < range[0]) {
            return bfprt(arr, L, range[0] - 1, index);
        } else {
            return bfprt(arr, range[1] + 1, R, index);
        }
    }

    public static int medianOfMedians(int[] arr, int L, int R) {
        int size = R - L + 1;
        int offset = size % 5 == 0 ? 0 : 1;
        int[] mArr = new int[size / 5 + offset];
        for (int team = 0; team < mArr.length; team++) {
            int teamFirst = L + team * 5;
            mArr[team] = getMedian(arr, teamFirst, Math.min(R, teamFirst + 4));
        }
        return bfprt(mArr, 0, mArr.length - 1, mArr.length / 2);
    }

    public static int getMedian(int[] arr, int L, int R) {
        insertionSort(arr, L, R);
        return arr[(L + R) / 2];
    }

    public static void insertionSort(int[] arr, int L, int R) {
        for (int i = L + 1; i <= R; i++) {
            for (int j = i - 1; j >= L && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
        test1();

        int[] arr = new int[] {2, 4, 5, 6, 1, 9, 3};
        int rs = findKthMin(arr, 2);
        System.out.println(rs);
    }

    private static void test1() {
        int testTime = 1000000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int k = (int) (Math.random() * arr.length) + 1;
            int ans1 = minKth1(arr, k);
            int ans2 = minKth2(arr, k);
            int ans3 = minKth3(arr, k);
            int rs = findKthMin(arr, k - 1);
            if (ans1 != ans2 || ans2 != ans3 || ans3 != rs) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
