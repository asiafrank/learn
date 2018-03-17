package com.asiafrank.sort;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Quick Sort
 * Created by Xiaofan Zhang on 24/3/2016.
 */
public class QuickSort {
    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        show(a);
        quickSort(a, 0, a.length);
    }

    private static void quickSort(Comparable[] a, int i, int hi) {
        if (i >= hi) return;
        int k = partition(a, i, hi);
        quickSort(a, i, k);
        quickSort(a, k+1, hi);
    }

    private static int partition(Comparable[] a, int i, int hi) {
        int lo = i;
        for (int j = lo + 1; j < hi; j++) {
            if (isLess(a[j], a[lo])) {
                i++;
                exchange(a, i, j);
            }
        }
        exchange(a, lo, i);
        return i;
    }

    @SuppressWarnings("unchecked")
    private static boolean isLess(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exchange(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (isLess(a[i], a[i-1]))
                return false;
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
        String[] a = {"Q","U","I","C","K","S","O","R","T","E","X","A","M","P","L","E"};
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
