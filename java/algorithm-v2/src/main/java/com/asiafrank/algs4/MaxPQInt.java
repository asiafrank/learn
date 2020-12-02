package com.asiafrank.algs4;

/**
 * 最大堆（int 类型作为元素，暂不考虑 comparator 比较器）
 *
 * @author zhangxiaofan 2020/12/02-10:54
 */
public class MaxPQInt {
    // 堆数组, 0 元素不使用,下标从 1 开始
    private int[] pq;
    // 堆中元素个数, 也可作为最后一个元素下标
    private int N;

    public MaxPQInt() {
        this.pq = new int[1024]; // 暂不考虑数组 resize
        N = 0;
    }

    /**
     * 插入堆底，并上浮
     */
    public void insert(int v) {
        N++;
        pq[N] = v;
        swim(N);
    }

    /**
     * 1.删除堆
     * 2.顶堆最后一个元素作为堆顶
     * 3.下沉
     */
    public int delMax() {
        int ret = pq[1];
        exch(1, N);
        N--;
        sink(1);
        return ret;
    }

    /**
     * 将下标为 k 的元素上浮
     * 与父结点比较，比父结点大，则交换，直到比父结点小
     */
    public void swim(int k) {
        int parent = parent(k);
        while (k > 1 && less(parent, k)) {
            exch(parent, k);
            k = parent;
        }
    }

    /**
     * 将下标为 k 的元素下沉
     * 与左右孩子比较，小的那个下沉
     */
    public void sink(int k) {
        while (k < N / 2) {
            int l = left(k);
            int r = right(k);

            int largest = r;
            if (less(largest, l)) { // l 与 r 比较，取大的
                largest = l;
            }

            if (less(largest, k)) { // largest 比 k 比较，取大的
                largest = k;
            }

            if (largest != k) { // largest 是某个孩子，k 下沉
                exch(largest, k);
            }
            k = largest;
        }
    }

    /**
     * 返回 true, a < b; false, a >= b
     */
    public boolean less(int a, int b) {
        return pq[a] < pq[b];
    }

    /**
     * 交换下标为 a, b 的元素
     */
    public void exch(int a, int b) {
        int t = pq[a];
        pq[a] = pq[b];
        pq[b] = t;
    }

    /**
     * 返回堆中最大值，即堆顶元素
     */
    public int max() {
        return pq[1];
    }

    /**
     * 返回堆元素个数
     */
    public int size() {
        return N;
    }

    /**
     * 返回下标 k 的父结点的下标
     */
    private int parent(int k) {
        return k / 2;
    }

    /**
     * 返回左孩子结点的下标
     */
    private int left(int k) {
        return k * 2;
    }

    /**
     * 返回右孩子结点的下标
     */
    private int right(int k) {
        return k * 2 + 1;
    }

    public static void main(String[] args) {
        MaxPQInt pq = new MaxPQInt();
        pq.insert(2);
        pq.insert(10);
        pq.insert(11);

        System.out.println(pq.max() == 11);
    }
}
