package coursexV2;

import com.asiafrank.TreeNode;

/**
 * 返回一棵树中，最大的搜索二叉子树大小
 *
 * 这棵树本身可能不是搜索二叉树，但是其子树可能是搜索二叉树。
 */
public class C12MaxSubBSTSize {

    public static int maxSubBSTSize(TreeNode root) {
        if (root == null)
            return 0;

        Info info = process(root);
        return info.maxSubBSTSize;
    }

    private static Info process(TreeNode n) {
        if (n == null)
            return null;

        int allSize = 1;
        int min = n.val;
        int max = n.val;

        Info lInfo = process(n.left);
        Info rInfo = process(n.right);
        if (lInfo != null) {
            allSize = lInfo.allSize + allSize;
            min = Math.min(lInfo.min, min);
            max = Math.max(lInfo.max, max);
        }

        if (rInfo != null) {
            allSize = rInfo.allSize + allSize;
            min = Math.min(rInfo.min, min);
            max = Math.max(rInfo.max, max);
        }

        int maxSubBSTSize = -1;
        if (lInfo != null) {
            maxSubBSTSize = Math.max(maxSubBSTSize, lInfo.maxSubBSTSize);
        }
        if (rInfo != null) {
            maxSubBSTSize = Math.max(maxSubBSTSize, rInfo.maxSubBSTSize);
        }

        boolean isLeftBST = lInfo == null || lInfo.isBST();
        boolean isRightBST = rInfo == null || rInfo.isBST();

        boolean isCurrentBST = true;
        if (lInfo != null) {
            isCurrentBST = lInfo.max < n.val;
        }
        if (rInfo != null) {
            isCurrentBST = isCurrentBST && rInfo.min > n.val;
        }
        if (isCurrentBST) { // 判断左子树和右子树是否是 BST
            isCurrentBST = isLeftBST && isRightBST;
        }

        if (isCurrentBST) { // 包含当前节点是 BST
            maxSubBSTSize = lInfo == null ? 0 : lInfo.maxSubBSTSize;
            maxSubBSTSize += rInfo == null ? 0 : rInfo.maxSubBSTSize;
            maxSubBSTSize += 1;
        }
        return new Info(maxSubBSTSize, allSize, min, max);
    }

    // 试试不返回 null 的版本
    private static Info processNoNull(TreeNode n) {
        if (n == null)
            return new Info(0, 0, Integer.MAX_VALUE, Integer.MIN_VALUE);

        Info lInfo = processNoNull(n.left);
        Info rInfo = processNoNull(n.right);
        int allSize = lInfo.allSize + rInfo.allSize + 1;
        int min = Math.min(Math.min(lInfo.min, rInfo.min), n.val);
        int max = Math.max(Math.max(lInfo.max, rInfo.max), n.val);

        int maxSubBSTSize = 1;

        // 判断当前树 是否是 BST
        boolean isCurrentBST = lInfo.max < n.val && rInfo.min > n.val;
        isCurrentBST = isCurrentBST && lInfo.isBST() && rInfo.isBST();
        if (isCurrentBST) { // 包括当前节点，都是 bst，则BST最大大小都加起来
            maxSubBSTSize = 1 + lInfo.maxSubBSTSize + rInfo.maxSubBSTSize;
        } else { // 不包括当前节点的 bst
            if (lInfo.isBST()) { // 左子树是 bst
                maxSubBSTSize = lInfo.maxSubBSTSize;
            }
            if (rInfo.isBST()) { // 右子树是 bst
                maxSubBSTSize = rInfo.maxSubBSTSize;
            }

            if (rInfo.isBST() && rInfo.isBST()) { // 左右都是bst，取大的值
                maxSubBSTSize = Math.max(lInfo.maxSubBSTSize, rInfo.maxSubBSTSize);
            }
        }
        return new Info(maxSubBSTSize, allSize, min, max);
    }

    public static class Info {
        int maxSubBSTSize; // 最大BST子树节点数
        int allSize; // 子树所有节点
        int min;
        int max;

        public Info(int maxSubBSTSize, int allSize, int min, int max) {
            this.maxSubBSTSize = maxSubBSTSize;
            this.allSize = allSize;
            this.min = min;
            this.max = max;
        }

        boolean isBST() {
            return allSize == maxSubBSTSize;
        }
    }
}
