package coursex;

import java.util.Stack;

/**
 * 树的遍历
 *
 * 先序遍历：头 左 右
 * 中序遍历：左 头 右
 * 后续遍历：左 右 头
 * @author zhangxiaofan 2020/08/20-10:24
 */
public class C07TreeWalk {
    public static void main(String[] args) {
        Node root = buildTree();
        preOrderRecursive(root);
        System.out.println("---------------------------");
        preOrder(root);
        System.out.println("===========================");
        inOrderRecursive(root);
        System.out.println("---------------------------");
        inOrder(root);
        System.out.println("===========================");
        postOrderRecursive(root);
        System.out.println("---------------------------");
        postOrder1(root);
        System.out.println("---------------------------");
        postOrder2(root);
    }

    //-----------------------------------------
    // 利用递归
    //-----------------------------------------

    /**
     * 递归 先序遍历
     */
    public static void preOrderRecursive(Node n) {
        if (n == null)
            return;

        print(n);
        preOrderRecursive(n.left);
        preOrderRecursive(n.right);
    }

    /**
     * 递归 中序遍历
     */
    public static void inOrderRecursive(Node n) {
        if (n == null)
            return;

        inOrderRecursive(n.left);
        print(n);
        inOrderRecursive(n.right);
    }

    /**
     * 递归 后序遍历
     */
    public static void postOrderRecursive(Node n) {
        if (n == null)
            return;

        postOrderRecursive(n.left);
        postOrderRecursive(n.right);
        print(n);
    }

    //-----------------------------------------
    // 非递归, 用栈模拟递归
    //-----------------------------------------

    /**
     * 非递归 先序遍历
     */
    public static void preOrder(Node root) {
        if (root == null)
            return;

        Stack<Node> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            Node n = stack.pop();
            print(n);
            Node l = n.left;
            Node r = n.right;
            // 由于栈的特性，要想先打印 左，则左应该后入栈
            if (r != null)
                stack.add(r);
            if (l != null)
                stack.add(l);
        }
    }

    /**
     * 非递归 中序遍历
     * 1.左边界依次入栈
     * 2.当 1 无法执行时，弹出并打印节点，然后其右子树再重复 1 操作。
     */
    public static void inOrder(Node root) {
       if (root == null)
           return;

       Stack<Node> stack = new Stack<>();
       stack.add(root);
       Node c = root;
       while (!stack.isEmpty()) {
           while (c != null) {
               Node l = c.left;
               if (l != null) {
                   stack.add(l);
               }
               c = l;
           }

           // c == null
           Node x = stack.pop();
           print(x);
           c = x.right;
           if (c != null)
               stack.add(c);
       }
    }

    /**
     * 非递归 后序遍历 方法1
     * 本质就是先序遍历的 print 换成进入 stack2，延后打印
     * 然后左右子节点压入 stack1 时 先左后右，因为 弹出 stack1 到 stack2 中时就变成了 先压右，后压左
     * 保证打印时是先左，后右。
     *
     * 流程：
     * 准备两个栈 stack1,stack2
     * 1. 弹出 stack1，压入 stack2
     * 2. 如果有左节点，则先压左
     * 3. 如果有右节点，则压入右
     * 4. 全部节点压入 stack2 后，弹出顺序就是后序遍历。
     */
    public static void postOrder1(Node root) {
        if (root == null)
            return;

        Stack<Node> stack1 = new Stack<>();
        stack1.add(root);
        Stack<Node> stack2 = new Stack<>();
        while (!stack1.isEmpty()) {
            Node n = stack1.pop();
            stack2.add(n);
            Node l = n.left;
            Node r = n.right;
            if (l != null)
                stack1.add(l);
            if (r != null)
                stack1.add(r);
        }

        while (!stack2.isEmpty()) {
            Node n = stack2.pop();
            print(n);
        }
    }

    /**
     * 非递归 后序遍历 方法2
     *
     * 一个栈实现，准备两个引用 h 和 c
     * h：指向上次打印的节点，即上次弹出的节点
     * c：当前栈顶的节点，就是应该后序打印的头节点。
     *
     * 流程
     * 1. c 存在左孩子，且c的左右孩子都没打印（h不等于左右孩子）
     *    则将 c 左孩子放入栈中
     * 2. c 存在右孩子，且c的右孩子没打印（h 不等于右孩子）
     *    则 c 的右孩子入栈
     * 3. 上面两个条件不满足，则弹出并打印 c，h 指向打印过的 c 节点。
     */
    public static void postOrder2(Node root) {
        if (root == null)
            return;
        Stack<Node> stack = new Stack<>();
        stack.add(root);
        Node h,c;
        h = root;
        while (!stack.isEmpty()) {
            c = stack.peek();
            Node l = c.left;
            Node r = c.right;
            if (l != null && h != l && h != r) {
                stack.add(l);
            } else if (r != null && h != r) {
                stack.add(r);
            } else {
                Node n = stack.pop();
                print(n);
                h = n;
            }
        }
    }

    public static void print(Node n) {
        System.out.println(n.value);
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
