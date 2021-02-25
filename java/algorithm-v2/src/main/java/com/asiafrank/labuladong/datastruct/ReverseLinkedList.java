package com.asiafrank.labuladong.datastruct;

/**
 * 递归翻转单链表
 * @author zhangxiaofan 2021/02/25-09:53
 */
public class ReverseLinkedList {
    private static class Node {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    /**
     * 递归翻转整个链表
     * @param node 头结点
     * @return 翻转整个单链表后的头结点
     */
    public static Node reverse(Node node) {
        Node next = node.next;
        if (next == null)
            return node;

        Node last = reverse(next);
        node.next = null;
        next.next = node;
        return last;
    }

    private static Node successor = null;

    /**
     * 翻转前N个节点
     * @param node 头结点
     * @return 新的头结点
     */
    public static Node reverseN(Node node, int n) {
        if (n == 1) {
            successor = node.next;
            return node;
        }

        Node next = node.next;
        Node last = reverseN(node.next, n - 1);
        next.next = node;
        node.next = successor;
        return last;
    }

    /**
     * 翻转从 m 到 n 的部分链表
     * @param node 链表
     * @param m    开始的节点
     * @param n    结束的节点
     * @return 头部
     */
    public static Node reverseBetween(Node node, int m, int n) {
        if (m == 1) {
            return reverseN(node, n);
        }

        node.next = reverseBetween(node.next, m - 1, n - 1);
        return node;
    }

    public static void main(String[] args) {
        Node head = createNode();
        printNode(head);

        // 翻转整个链表
        head = reverse(head);
        printNode(head);

        // 翻转部分链表
        head = createNode();
        head = reverseN(head, 3);
        printNode(head);

        // 翻转部分链表 m,n
        head = createNode();
        head = reverseBetween(head, 2, 4);
        printNode(head);
    }

    public static void printNode(Node head) {
        Node n = head;
        while (n != null) {
            System.out.print(n.val + "->");
            n = n.next;
        }
        System.out.print("null");
        System.out.println();
    }

    private static Node createNode() {
        Node head = new Node(1);
        Node a = new Node(2);
        head.next = a;
        Node b = new Node(3);
        a.next = b;
        Node c = new Node(4);
        b.next = c;
        Node d = new Node(5);
        c.next = d;
        return head;
    }
}
