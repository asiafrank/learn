package com.asiafrank.st;

/**
 *
 * Created by Xiaofan Zhang on 28/3/2016.
 */
public class SequentialSearchST<Key, Value> {
    private Node first;
    private class Node {
        Key key;
        Value val;
        Node next;
        public Node(Key k, Value v, Node next) {
            this.key = k;
            this.val = v;
            this.next = next;
        }
    }

    public Value get(Key k) {
        for (Node x = first; x != null; x = x.next) {
            if (k.equals(x.key)){
                return x.val;
            }
        }
        return null;
    }

    public void put(Key k, Value v) {
        for (Node x = first; x != null; x = x.next) {
            if (k.equals(x.key)) {
                x.val = v;
                return;
            }
        }
        first = new Node(k, v, first);
    }
}
