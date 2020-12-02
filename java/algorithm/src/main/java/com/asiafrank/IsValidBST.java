package com.asiafrank;

/**
 * 判断一棵树是否是 BST
 * BST：左子树的每个节点都小于右子树的每个节点
 * @author zhangxiaofan 2020/12/02-17:00
 */
public class IsValidBST {

    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, null, null);
    }

    /**
     * 递归匹配左子树，右子树
     * @param root 当前节点
     * @param min  最小节点，root 必须大于该节点，才满足要求
     * @param max  最大节点，root 必须小于该节点，才满足要求
     * @return true, 是 BST；false，不是BST
     */
    private boolean isValidBST(TreeNode root, TreeNode min, TreeNode max) {
        if (root == null)
            return true;

        TreeNode left = root.left;
        TreeNode right = root.right;

        if (min != null && root.val <= min.val)
            return false;
        if (max != null && root.val >= max.val)
            return false;
        return isValidBST(left, min, root) && isValidBST(right, root, max);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(1);
        TreeNode right = new TreeNode(0);
        root.left = left;
        root.right = right;

        IsValidBST p = new IsValidBST();
        boolean validBST = p.isValidBST(root);
        System.out.println(validBST);
    }
}
