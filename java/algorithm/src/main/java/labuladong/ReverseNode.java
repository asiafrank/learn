package labuladong;

import coursexV2.util.Node;

/**
 * 递归翻转链表
 * @author zhangxiaofan 2020/12/14-16:56
 */
public class ReverseNode {

    /**
     * 翻转整个链表
     */
    public static Node reverse(Node node) {
        if (node.next == null)
            return node;

        Node last = reverse(node.next);
        node.next.next = node;
        node.next = null;
        return last;
    }

    public static Node successor; // 后继
    /**
     * 翻转部分列表
     */
    public static Node reverseN(Node node, int n) {
        if (n == 1) {
            successor = node.next;
            return node;
        }

        Node last = reverseN(node.next, n - 1);
        node.next.next = node;
        node.next = successor;
        return last;
    }

    /**
     * 翻转从 m 到 n 的部分链表
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
