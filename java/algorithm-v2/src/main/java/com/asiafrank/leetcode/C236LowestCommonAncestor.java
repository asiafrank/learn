package com.asiafrank.leetcode;

/**
 * 236. 二叉树的最近公共祖先
 * https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/
 *
 * 思路：递归遍历二叉树，p,q 为节点，需要找到这两个节点的公共祖先。
 * - 当 p,q 分别在左右子树中时，root 为公共祖先。即当 left,right 都不为空时，返回 root
 * - 当 left 不为空时，right 为空，则返回的 left 就是公共祖先
 * - 当 right 不为空，left 为空时，则返回的 right 就是公共祖先
 * - 递归遍历时，遇到 p，遇到 q 则返回 root，因为有可能 p 和 q 本身就是两个节点最近公共祖先
 * @author zhangxiaofan 2021/03/03-09:42
 */
public class C236LowestCommonAncestor {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    /**
     * 最终返回值如果是 null 则没有找到最近公共祖先
     * 如果不为 null，则这个返回值就是最近公共祖先
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || p == root || q == root)
            return root; // 遇到了 p 或 q 就返回自己。自己有可能也是两者的最近公共祖先

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null)
            return root; // left, right 都不为空，代表 p 和 q 在两侧，root 就是公共祖先
        if (left == null && right != null)
            return right; // 当 right 不为空，left 为空时，则返回的 right 就是公共祖先
        if (left != null && right == null)
            return left; // 当 left 不为空时，right 为空，则返回的 left 就是公共祖先
        return root;
    }
}
