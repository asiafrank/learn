package coursexV2;

import com.asiafrank.TreeNode;

/**
 * 判断是否是平衡二叉树
 *
 * 左右子树高度差小于等于1
 */
public class C12IsBalanced {

    public static class Info {
        public boolean isBalanced;
        public int height;

        public Info(boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }

    public static boolean isBalanced(TreeNode root) {
        Info info = process(root);
        return info.isBalanced;
    }

    private static Info process(TreeNode n) {
        if (n == null)
            return new Info(true, 0);

        Info lInfo = process(n.left);
        Info rInfo = process(n.right);
        int h = Math.max(lInfo.height, rInfo.height) + 1;
        boolean balanced = true;
        if (lInfo.isBalanced && rInfo.isBalanced) {
            int delta = lInfo.height - rInfo.height;
            if (Math.abs(delta) > 1) { // 高度差大于1
                balanced = false;
            }
        } else {
            // 其中一个子树不平衡
            balanced = false;
        }
        return new Info(balanced, h);
    }
}
