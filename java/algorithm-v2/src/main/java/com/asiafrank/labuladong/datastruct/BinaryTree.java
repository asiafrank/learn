package com.asiafrank.labuladong.datastruct;

/**
 * 二叉树操作集锦
 * @author zhangxiaofan 2021/02/15-13:28
 */
public class BinaryTree {
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    /**
     * 二叉树遍历加1
     */
    public void plusOne(TreeNode root) {
        if (root == null)
            return;

        root.val = root.val + 1;
        plusOne(root.left);
        plusOne(root.right);
    }

    /**
     * 判断两棵树是否相等
     */
    public boolean isSameTree(TreeNode a, TreeNode b) {
        // 两个都是 null，true
        if (a == null && b == null)
            return true;

        // 两个其中一个不是 null, false
        if (a == null || b == null)
            return false;

        if (a.val != b.val) // 不相等
            return false;

        return isSameTree(a.left, b.left) && isSameTree(a.right, b.right);
    }

    /**
     * 判断一个树是不是二叉树
     * 二叉树是左孩子小于父亲，右孩子大于父亲
     * 每个节点都要小于右子树的所有节点，
     * 每个节点都要大于左子树的所有节点
     */
    public boolean isValidTree(TreeNode root, TreeNode min, TreeNode max) {
        if (root == null)
            return true;

        if (min != null && min.val >= root.val)
            return false;
        if (max != null && max.val <= root.val)
            return false;

        return isValidTree(root.left, min, root)
                && isValidTree(root.right, root, max);
    }

    /**
     * 判断 target 值在 BST 中是否存在
     */
    public boolean isInBST(TreeNode root, int target) {
        if (root == null)
            return false;

        if (root.val == target)
            return true;
        else if (root.val > target) { // root 值大了，所以往左子树找
            return isInBST(root.left, target);
        }
        else { // root.val < target
            return isInBST(root.right, target);
        }
    }
}
