package com.asiafrank.course;

import java.util.Stack;

/**
 * 树的遍历
 * @author zhangxiaofan 2021/03/04-10:27
 */
public class TreeIterate {
    /**
     * 递归先序遍历
     * @param root 根结点
     */
    public static void preOrderRecursive(Node root) {
        if (root == null)
            return;

        System.out.print(root.value + ",");
        preOrderRecursive(root.left);
        preOrderRecursive(root.right);
    }

    /**
     * 迭代先序遍历
     * 使用栈.
     * 1.弹出就打印
     * 2.先放入右结点，再放入左结点
     * @param root 根结点
     */
    public static void preOrder(Node root) {
        if (root == null)
            return;

        Stack<Node> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            Node n = stack.pop();
            System.out.print(n.value + ",");
            Node l = n.left;
            Node r = n.right;
            if (r != null)
                stack.add(r);
            if (l != null)
                stack.add(l);
        }
    }

    /**
     * 递归中序遍历
     * @param root 根结点
     */
    public static void inOrderRecursive(Node root) {
        if (root == null)
            return;

        inOrderRecursive(root.left);
        System.out.print(root.value + ",");
        inOrderRecursive(root.right);
    }

    public static void inOrder(Node root) {
        if (root == null)
            return;

        Stack<Node> stack = new Stack<>();
        stack.add(root);
        Node c = root;
        while (!stack.isEmpty()) {
            while (c != null) {
                Node l = c.left;
                if (l != null)
                    stack.add(l);
                c = l;
            }

            Node n0 = stack.pop();
            System.out.print(n0.value + ",");
            c = n0.right;
            if (c != null)
                stack.add(c);
        }
    }

    public static void main(String[] args) {
        Node root = buildTree();
        preOrderRecursive(root);
        System.out.println("\n---------------------------\n");
        preOrder(root);
        System.out.println("\n===========================\n");
        inOrderRecursive(root);
        System.out.println("\n---------------------------\n");
        inOrder(root);
        System.out.println("\n===========================\n");
//        postOrderRecursive(root);
//        System.out.println("\n---------------------------\n");
//        postOrder1(root);
//        System.out.println("\n---------------------------\n");
//        postOrder2(root);
    }

    public static class Node {
        Node left;
        Node right;
        int value;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * <pre>
     *       7
     *     /   \
     *    5     9
     *   / \   / \
     *  4   6 8   10
     * </pre>
     * @return head node
     */
    private static Node buildTree() {
        Node root = new Node(7);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n8 = new Node(8);
        Node n9 = new Node(9);
        Node n10 = new Node(10);

        root.left = n5;
        root.right = n9;

        n5.left = n4;
        n5.right = n6;

        n9.left = n8;
        n9.right = n10;
        return root;
    }
}
