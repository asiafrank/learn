package com.asiafrank.sort;

import edu.princeton.cs.algs4.StdOut;

/**
 * Dijkstra
 * 大量重复元素的数组, 三向切分算法比标准的快速排序效率高.
 *
 * Created by Xiaofan Zhang on 24/3/2016.
 */
public class Quick3way {
    public static void sort(Comparable[] a) {
        quick3way(a, 0, a.length - 1);
    }

    @SuppressWarnings("unchecked")
    private static void quick3way(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int lt = lo, i = lo + 1, gt = hi;
        Comparable v = a[lo];
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if      (cmp < 0) exchange(a, lt++, i++);
            else if (cmp > 0) exchange(a, i, gt--);
            else              i++;
        }
        quick3way(a, lo, lt - 1);
        quick3way(a, gt + 1, hi);
    }

    private static void exchange(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    @SuppressWarnings("unchecked")
    private static boolean isLess(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static boolean isSorted(String[] a) {
        for (int i = 1; i < a.length; i++) {
            if (isLess(a[i], a[i-1]))
                return false;
        }
        return true;
    }

    private static void show(String[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        String[] a = {"R","B","W","W","R","W","B","R","R","W","B","R"};
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
