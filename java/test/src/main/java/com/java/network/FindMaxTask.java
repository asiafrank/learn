package com.java.network;

import java.util.concurrent.Callable;

/**
 * Example 3-9.
 */
public class FindMaxTask implements Callable<Integer> {
    private int[] data;
    private int start;
    private int end;

    public FindMaxTask(int[] data, int end, int start) {
        this.data = data;
        this.end = end;
        this.start = start;
    }

    @Override
    public Integer call() throws Exception {
        int max = Integer.MIN_VALUE;
        for (int i = start; i < end; i++) {
            if (data[i] > max) max = data[i];
        }
        return max;
    }
}
