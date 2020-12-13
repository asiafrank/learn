package coursexV2;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 431
 * 将一棵多叉树编码为二叉树
 * 设计一个算法，可以将 N 叉树编码为二叉树，并能将该二叉树解码为原 N 叉树。
 * 一个 N 叉树是指每个节点都有不超过 N 个孩子节点的有根树。
 * 类似地，一个二叉树是指每个节点都有不超过 2 个孩子节点的有根树。
 * 你的编码 / 解码的算法的实现没有限制，你只需要保证一个 N 叉树可以编码为二叉树且该二叉树可以解码回原始 N 叉树即可。
 *
 * 注意：
 * N 的范围在 [1, 1000]
 * 不要使用类成员 / 全局变量 / 静态变量来存储状态。
 * 你的编码和解码算法应是无状态的。
 *
 * 方法：将 x 节点的孩子，放在左节点的右边界上
 */
public class C11EncodeNaryTreeToBinaryTree {

    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, List<Node> children) {
            this.val = val;
            this.children = children;
        }
    }

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    class Codec {
        // 将 n 叉树编码成 二叉树
        public TreeNode encode(Node root) {
            TreeNode n = new TreeNode(root.val);
            n.left = en(root.children);
            return n;
        }

        private TreeNode en(List<Node> children) {
            if (children == null || children.isEmpty())
                return null;

            TreeNode head = null;
            TreeNode pre = null;
            for (Node c : children) {
                TreeNode n = new TreeNode(c.val);
                if (head == null) {
                    head = n;
                } else { // 多叉孩子，挂在右侧，形成单链表
                    pre.right = n;
                }
                pre = n;
                n.left = en(c.children);
            }
            return head;
        }

        // 将 二叉树 解码成 n 叉树
        public Node decode(TreeNode root) {
            Node n = new Node(root.val);
            n.children = de(root.left);
            return n;
        }

        private List<Node> de(TreeNode head) {
            if (head == null)
                return null;

            TreeNode curr = head;
            List<Node> children = new ArrayList<>();
            while (curr != null) { // 右侧单链表，合并成多叉孩子
                Node n = new Node(curr.val);
                n.children = de(curr.left);

                curr = curr.right;
                children.add(n);
            }
            return children;
        }
    }
}
