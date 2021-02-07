package com.asiafrank.labuladong.dp;

/**
 * 求一棵二叉树的最大值(非 DP)
 * @author zhangxiaofan 2021/02/07-15:45
 */
public class MaxNodeVal {

    public static class TreeNode {
        int val; // 假设 val 必须大于等于0
        TreeNode left;
        TreeNode right;
    }

    public static int maxVal(TreeNode root) {
        if (root == null)
            return -1;

        int l = maxVal(root.left);
        int r = maxVal(root.right);
        return Math.max(l, r);
    }
}
