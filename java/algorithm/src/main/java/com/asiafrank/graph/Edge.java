package com.asiafrank.graph;

/**
 * 边
 */
public class Edge {
    private int from; // 从哪个节点来
    private int to; // 到哪里去
    private int weight; // 权重

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
