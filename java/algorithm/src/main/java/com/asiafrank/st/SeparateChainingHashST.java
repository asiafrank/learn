package com.asiafrank.st;

public class SeparateChainingHashST<Key, Value> {
    private int N;
    private int M;
    private SequentialSearchST<Key, Value>[] st;

    public SeparateChainingHashST() {
        this(997);
    }

    public SeparateChainingHashST(int M) {
        this.M = M;
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
        for (int i = 0; i < M; i++)
            st[i] = new SequentialSearchST();
    }

    private int hash(Key k) {
        return (k.hashCode() & 0x7fffffff) % M;
    }

    public Value get(Key k) {
        return (Value)st[hash(k)].get(k);
    }

    public void put(Key k, Value v) {
        st[hash(k)].put(k, v);
    }

    //...
}
