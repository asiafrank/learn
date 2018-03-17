package com.asiafrank.sort.bak;

import edu.princeton.cs.algs4.StdOut;

/**
 * Insertion use Comparable
 * Created by Xiaofan Zhang on 23/3/2016.
 */
public class Insertion2 {
    public static void sort(Comparable[] a) {
        Comparable key;
        for (int i = 1; i < a.length; i++) {
            key = a[i];
            int j = i - 1;
            while (j >= 0 && isLess(key, a[j])) {
                a[j+1] = a[j];
                j--;
            }
            a[j+1] = key;
        }
    }

    @SuppressWarnings("unchecked")
    private static boolean isLess(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static boolean isSorted(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            if (isLess(a[i], a[i-1])) {
                return false;
            }
        }
        return true;
    }

    public static void show(Comparable[] a) {
        for (Comparable c : a) {
            StdOut.print(c + " ");
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
