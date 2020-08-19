package course;

public class Course03MergeSort {
    public static void main(String[] args) {

    }

    public static void mergeSort(int[] arr, int l, int r) {
        if (l == r) {
            return;
        }

        int mid = (r - l) / 2 + l;
        mergeSort(arr, l, mid);
        mergeSort(arr, mid + 1, r);
        merge(arr, l, mid, r);
    }

    /**
     * 归并：
     * 1. help数组，排序好的数放在这里
     * 2. 左边元素小于等于右边数组的放在 help 槽中
     * 3. p1 越界时，p1 的元素放 help 最后
     *    p2 越界时，p2 的元素放 help 最后
     * 4. 将排好序的元素拷贝回原数组
     * @param arr 数组
     * @param l   左边开始下标
     * @param mid 左边结束下标
     * @param r   右边结束下标
     */
    private static void merge(int[] arr, int l, int mid, int r) {
        // 准备 help 数组
        int[] help = new int[r - l + 1];
        int i = 0;
        int p1 = l;
        int p2 = mid + 1;
        while (p1 <= mid && p2 <= r) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        // 左右数组剩下的那个元素追加到 help 中
        while (p1 <= mid) {
            help[i++] = arr[p1++];
        }
        while (p2 <= r) {
            help[i++] = arr[p2++];
        }
        // help 复制回 arr 中
        for (i = 0; i < help.length; i++) {
            arr[i + l] = help[i];
        }
    }

    // TODO： 非递归做法
}
