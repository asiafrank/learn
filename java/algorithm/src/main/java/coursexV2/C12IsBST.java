package coursexV2;

import com.asiafrank.TreeNode;

/**
 * 判断是否是搜索二叉树
 * 每一棵子树，左树都比头小，右树都比头大
 */
public class C12IsBST {

    public static boolean isBST(TreeNode root) {
        if (root == null)
            return false;

        Info info = process(root);
        return info.isBST;
    }

    private static Info process(TreeNode node) {
        if (node == null) {
            return null;
        }

        Info lInfo = process(node.left);
        Info rInfo = process(node.right);
        int v = node.val;
        int min = v;
        int max = v;
        if (lInfo != null) {
            min = Math.min(lInfo.min, min);
            max = Math.min(lInfo.max, max);
        }
        if (rInfo != null) {
            min = Math.min(rInfo.min, min);
            max = Math.max(rInfo.max, max);
        }

        boolean isBST = true;
        if (lInfo != null) {
            if (!lInfo.isBST) {
                isBST = false;
            }

            if (lInfo.max >= v) { // 左子树最大值大于当前节点值，不是搜索二叉树
                isBST = false;
            }
        }

        if (rInfo != null) {
            if (!rInfo.isBST) {
                isBST = false;
            }

            if (rInfo.min <= v) { // 右子树最小值小于当前节点值，不是搜索二叉树
                isBST = false;
            }
        }
        return new Info(isBST, min, max);
    }

    public static class Info {
        boolean isBST;
        int min;
        int max;

        public Info(boolean isBST, int min, int max) {
            this.isBST = isBST;
            this.min = min;
            this.max = max;
        }
    }
}
