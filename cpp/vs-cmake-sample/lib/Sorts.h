#pragma once
#include <climits>

namespace clrs {
    /**
     * 插入排序 P9
     * 时间复杂度，最好情况已经有序 O(n)，最坏情况倒序 O(n^2)
     * 空间复杂度 O(1)，原址排序
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
     * @param len 数组的长度
     */
    void InsertSort(int a[], int len) 
    {
        for (int j = 1; j < len; j++)
        {
            int key = a[j];
            int i = j - 1;
            while (i >= 0 && key < a[i])
            {
                a[i+1] = a[i];
                i = i - 1;
            }
            a[i+1] = key;
        }
    }

    //----------------------------
    // 归并排序
    //----------------------------

    void Merge(int a[], int lo, int mi, int hi);
    /**
     * 归并排序
     * 时间复杂度 O(nlgn)
     * 空间复杂度 O(n)
     *
     * 给定一个数组
     *  索引：   0   1   2   3   4   5   6
     *         ---------------------------
     *  数组： | 7 | 6 | 5 | 4 | 3 | 2 | 1 |
     *         ---------------------------
     * @param a 需要归并的数组
     * @param lo a数组里，需要排序的范围开始下标
     * @param hi a数组里，需要排序的范围结束下标
     */
    void MergeSort(int a[], int lo, int hi)
    {
        if (lo >= hi)
            return;
        int mi = (lo + hi) / 2;
        MergeSort(a, lo, mi); // left
        MergeSort(a, mi + 1, hi); // right
        Merge(a, lo, mi, hi);
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
     * @param a 需要归并的数组
     * @param lo a数组里，排好序的第一个数组范围开始下标
     * @param mi a数组里，排好序的第一个数组范围的结束下标
     * @param hi a数组里，排好序的第二个数组范围的结束下标
     */
    void Merge(int a[], int lo, int mi, int hi)
    {
        int n1 = mi - lo + 1;
        int n2 = hi - mi;
        int *left = new int[n1 + 1], *right = new int[n2 + 1];
        for (int i = 0; i < n1; i++)
            left[i] = a[lo + i];
        for (int j = 0; j < n2; j++)
            right[j] = a[mi + j + 1];
        
        left[n1] = INT_MAX;
        right[n2] = INT_MAX;
        for (int k = lo, i = 0, j = 0; k <= hi; k++) 
        {
            int l = left[i];
            int ri = right[j];
            if (l <= ri)
            {
                a[k] = l;
                i++;
            }
            else
            {
                a[k] = ri;
                j++;
            }
        }

        delete[] left;
        delete[] right;
    }

    //----------------------------
    // P23习题2-2，冒泡排序
    //----------------------------

    /**
     * 冒泡排序
     * 时间复杂度，最好情况已经有序 O(n)，最坏情况倒序 O(n^2)
     * 空间复杂度 O(1)，原址排序
     *
     *  索引：   0   1   2   3   4   5   6
     *         ---------------------------
     *  数组： | 6 | 7 | 4 | 5 | 3 | 2 | 1 |
     *         ---------------------------
     *          i                       j
     * 0~i-1 为已排好序的部分。
     * i~n-1 为未排好序的部分
     * 在未排好序的部分中，比较中相对小的元素一步一步向左推移（冒泡）：一次内循环完毕后
     * 未排好序的部分中最小的元素到达 i 下标处，至此 0~i 编程已排好序的部分，这时 i++ 准备下一次循环。
     *
     * @param a length 大于等于 2 的数组
     * @param len 数组的长度
     */
    void BubbleSort(int a[], int len)
    {
        for (int i = 0; i < len; i++)
        {
            for (int j = len-1; j >= i+1; j--)
            {
                if (a[j] < a[j - 1])
                {
                    int t = a[j];
                    a[j] = a[j - 1];
                    a[j - 1] = t;
                }
            }
        }
    }

    //----------------------------
    // P95,快速排序
    //----------------------------
    
    /**
     * 快速排序-分解
     * 图解见 P96
     *
     * @param a length 大于等于 2 的数组
     * @param p 数组的开始下标
     * @param r 数组的结束下标
     * p和r参数，输入时，注意不能超出界限
     *
     * @return 主元右边开始的下标
     */
    int Partition(int a[], int p, int r)
    {
        int x = a[r];
        int i = p - 1;
        for (int j = p; j < r; j++)
        {
            if (a[j] <= x)
            {
                i = i + 1;
                int t = a[j];
                a[j] = a[i];
                a[i] = t;
            }
        }
        int t = a[i + 1];
        a[i + 1] = a[r];
        a[r] = t;
        return i + 1;
    }

    /**
     * 快速排序
     * 时间复杂度，最好情况左右划分均匀 O(nlgn)，最坏情况左右划分始终不均匀 O(n^2)
     * 平均情况 O(nlgn)
     * 空间复杂度 O(1)，原址排序
     *
     * @param a length 大于等于 2 的数组
     * @param p 数组的开始下标
     * @param r 数组的结束下标
     * p和r参数，输入时，注意不能超出界限
     */
    void QuickSort(int a[], int p, int r)
    {
        if (p >= r)
            return;

        int q = Partition(a, p, r);
        QuickSort(a, p, q - 1);
        QuickSort(a, q + 1, r);
    }

    //----------------------------
    // P85,堆排序
    //----------------------------

    /**
     * 由当前结点的下标，获取它的左子结点的下标
     */
    int Left(int i)
    {
        return 2 * i;
    }

    /**
     * 由当前结点的下标，获取它的右子结点的下标
     */
    int Right(int i)
    {
        return 2 * i + 1;
    }

    /**
     * 由当前结点的下标，获取它的父结点的下标
     */
    int Parent(int i)
    {
        return i / 2;
    }

    /**
     * 维护最大堆性质
     * 时间复杂度，O(lgn)
     *
     * @param a   0元素为空的，用于排序的数组
     * @param len 数组的长度
     * @param i   大于0的数组下标
     */
    void MaxHeapify(int a[], int len, int i)
    {
        if (i >= len)
            return;

        int largest = i;
        int l = Left(i);
        int r = Right(i);
        if (l < len && a[l] > a[largest])
            largest = l;
        if (r < len && a[r] > a[largest])
            largest = r;
        if (largest != i)
        {
            int t = a[largest];
            a[largest] = a[i];
            a[i] = t;
            MaxHeapify(a, len, largest);
        }
    }

    /**
     * 创建一个最大堆
     * 时间复杂度 O(nlgn)
     *
     * @param a   0元素为空的，用于排序的数组
     * @param len 数组的长度
     */
    void BuildMaxHeap(int a[], int len)
    {
        int heapSize = len - 1;
        for (int i = heapSize/2; i > 0; i--)
        {
            MaxHeapify(a, len, i);
        }
    }

    /**
     * 堆排序
     * 时间复杂度，O(nlgn)
     * 空间复杂度 O(1)，原址排序
     *
     * @param a   0元素为空的，用于排序的数组
     * @param len 数组的长度
     */
    void HeapSort(int a[], int len)
    {
        BuildMaxHeap(a, len);
        for (int heapSize = len - 1; heapSize > 1; heapSize--)
        {
            int t = a[heapSize];
            a[heapSize] = a[1];
            a[1] = t;
            MaxHeapify(a, heapSize, 1);
        }
    }

} // clrs end
