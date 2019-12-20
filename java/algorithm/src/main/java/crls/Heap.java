package crls;

/**
 * 堆
 * 最大堆使用 maxHeapify
 * 最大堆的性质：a[parent(i)] >= a[i]
 *
 * 最小堆使用 minHeapify
 * 最大堆的性质：a[parent(i)] <= a[i]
 *
 * 注：
 * 下面数组 a 下标从 0 开始；
 * heapSize 与书中 heap-size 不同的是，heapSize 指元素个数，和 A.length 同义。
 * 当 heapSize = A.length 时，程序中 A[heapSize] 越界
 *
 *
 * @author zhangxiaofan 2019/12/19-14:32
 */
public final class Heap {

    /**
     * 堆排序
     * 1. 建立最大堆
     * 2. 交换堆根节点到数组最后一项
     * 3. heapSize 减一
     * 4. 维护堆的性质
     * 5. 2-4 重复执行，直到 heapSize = 1
     * 如此，便能排序成一个从小到大的数组
     *
     * @param a 需要排序的数组
     */
    public static void heapSort(int[] a) {
        buildMaxHeap(a, a.length);
        for (int heapSize = a.length; heapSize > 0;) {
            int t = a[0];
            a[0] = a[heapSize - 1];
            a[heapSize - 1] = t;

            heapSize--;
            maxHeapify(a, 0, heapSize);
        }
    }

    /**
     * 创建最大堆
     * 自底向上构建堆
     *
     * firstIndex + 1 ... a.length 都是叶节点
     * @param a 数组
     */
    public static void buildMaxHeap(int[] a, int heapSize) {
        int firstIndex = parent(heapSize - 1);
        for (int i = firstIndex; i >= 0; i--) {
            maxHeapify(a, i, heapSize);
        }
    }

    /**
     * 维护最大堆的性质
     * 1.设当前元素为父节点，找到两个孩子节点进行比较
     * 2.如果父节点不是三者最大节点，则父节点与最大的孩子节点交换值
     * 3.如果交换过，则交换过的孩子孩子节点下标递归传入 maxHeapify 中
     * @param a 堆数组
     * @param i 当前元素
     * @param heapSize 堆大小
     */
    private static void maxHeapify(int[] a, int i, int heapSize) {
        int l = left(i);
        int r = right(i);
        int largest;

        if (r >= heapSize)
            return;

        if (a[l] <= a[r]) { // 左右孩子比较
            largest = r;
        } else {
            largest = l;
        }

        if (a[largest] <= a[i]) { // 与父节点比较
            largest = i;
        }

        if (largest != i) {
            int t = a[largest];
            a[largest] = a[i];
            a[i] = t;

            maxHeapify(a, largest, heapSize);
        }
    }

    /**
     * 获取 i 的父节点下标
     * @param i 当前节点下标
     * @return 父节点下标
     */
    private static int parent(int i) {
        return (i - 1) / 2;
    }

    /**
     * 获取 i 的左孩子节点下标
     * @param i 当前节点下标
     * @return 左孩子节点下标
     */
    private static int left(int i) {
        return (i << 1) + 1; // 2 * i + 1
    }

    /**
     * 获取 i 的右孩子节点下标
     * @param i 当前节点下标
     * @return 右孩子节点下标
     */
    private static int right(int i) {
        return (i + 1) << 1; // 2 * (i + 1)
    }
}
