package coursexV2;

import com.asiafrank.TreeNode;

/**
 * 给定一棵二叉树的头节点 head，任何两个点之间都存在距离。
 * 返回整棵二叉树的最大距离。
 */
public class C12MaxDistance {

    public static int maxDistance(TreeNode root) {
        if (root == null)
            return 0;

        return process(root).maxDistance;
    }

    private static Info process(TreeNode n) {
        if (n == null)
            return new Info(0, 0);

        Info lInfo = process(n.left);
        Info rInfo = process(n.right);

        int height = Math.max(lInfo.height, rInfo.height) + 1;
        int maxDistance = lInfo.height + rInfo.height + 1;
        maxDistance = Math.max(lInfo.maxDistance, maxDistance);
        maxDistance = Math.max(rInfo.maxDistance, maxDistance);
        return new Info(maxDistance, height);
    }


    public static class Info {
        int maxDistance;
        int height;

        public Info(int maxDistance, int height) {
            this.maxDistance = maxDistance;
            this.height = height;
        }
    }
}
