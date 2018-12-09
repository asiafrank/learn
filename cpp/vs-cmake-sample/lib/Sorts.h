#pragma once
#include <climits>

namespace clrs {
    /**
     * 插入排序 P9
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
        int left[n1 + 1], right[n2 + 1];
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
    }

} // clrs end
