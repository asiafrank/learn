package com.asiafrank.st;

public class LinearProbingHashST<Key, Value> {
    private int N;
    private int M = 16;
    private Key[] keys;
    private Value[] vals;

    public LinearProbingHashST() {
        keys = (Key[]) new Object[M];
        vals = (Value[]) new Object[M];
    }

    public LinearProbingHashST(int M) {
        this.M = M;
        keys = (Key[]) new Object[M];
        vals = (Value[]) new Object[M];
    }

    private int hash(Key k) {
        return (k.hashCode() & 0x7fffffff) % M;
    }

    private void resize(int cap) {
        LinearProbingHashST<Key, Value> t;
        t = new LinearProbingHashST<Key, Value>(cap);
        for (int i = 0; i < M; i++) {
            if (keys[i] !=null)
                t.put(keys[i], vals[i]);
        }
        keys = t.keys;
        vals = t.vals;
        M = t.M;
    }

    public void put(Key k, Value v) {
        if (N >= M/2) resize(2*M);
        int i;
        for (i = hash(k); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(k)) {
                vals[i] = v;
                return;
            }
        }
        keys[i] = k;
        vals[i] = v;
        N++;
    }

    public Value get(Key k) {
        for (int i = hash(k); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(k)) {
                return vals[i];
            }
        }
        return null;
    }

    public void delete(Key k) {
        if (!contain(k)) return;
        int i = hash(k);
        while (!k.equals(keys[i])) {
            i = (i + 1) % M;
        }
        keys[i] = null;
        vals[i] = null;
        i = (i + 1) % M;

        while (keys[i] != null) {
            Key keyToRedo = keys[i];
            Value valToRedo = vals[i];
            keys[i] = null;
            vals[i] = null;
            N--;
            put(keyToRedo, valToRedo);
            i = (i + 1) % M;
        }
        N--;
        if (N > 0 && N == M/8) resize(M/2);
    }

    private boolean contain(Key k) {
        for (int i = hash(k); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(k)) {
                return true;
            }
        }
        return false;
    }
}
