package com.asiafrank.sort;

import edu.princeton.cs.algs4.StdOut;

/**
 * Bottom-up Merge Sort
 * Created by Xiaofan Zhang on 24/3/2016.
 */
public class MergeBU {
    private static Comparable[] aux; // auxiliary array

    public static void sort(Comparable[] a) {
        int N = a.length;
        aux = new Comparable[N];
        for (int sz = 1; sz < N; sz = sz + sz) {
            for (int lo = 0; lo < N - sz; lo += (sz + sz))
                merge(a, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
        }
    }

    private static void merge(Comparable[] a, int lo, int mid, int hi) {
        int i = lo, j= mid + 1;
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];

        for (int k = lo; k <= hi; k++)
            if      (i > mid)                a[k] = aux[j++];
            else if (j > hi)                 a[k] = aux[i++];
            else if (isLess(aux[j], aux[i])) a[k] = aux[j++];
            else                             a[k] = aux[i++];
    }

    @SuppressWarnings("unchecked")
    private static boolean isLess(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (isLess(a[i], a[i-1])) {
                return false;
            }
        }
        return true;
    }

    public static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        String[] a = {"M","E","R","G","E","S","O","R","T","E","X","A","M","P","L","E"};
        sort(a);
        assert isSorted(a);
        show(a);
    }
}