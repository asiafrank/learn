package coursexV2;

import com.asiafrank.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 按层遍历
 */
public class C11LevelTraversalBT {

    public static void level(TreeNode root) {
        if (root == null)
            return;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode n = queue.poll();
            System.out.println(n.val);
            TreeNode left = n.left;
            TreeNode right = n.right;
            if (left != null)
                queue.add(left);
            if (right != null)
                queue.add(right);
        }
    }
}
