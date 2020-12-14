package coursexV2;

import com.asiafrank.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 是否是完全二叉树
 *
 * 过程：
 * 1. 按层遍历
 * 2. 当遇到有右孩子，无左孩子的节点，必定不是完全二叉树
 * 3. 如果遇到一个节点是叶结点，则其后所有的节点必定都是叶结点。
 */
public class C12IsCBT {

    /**
     * 按层遍历
     */
    public static boolean isCBT(TreeNode root) {
        if (root == null)
            return true;

        boolean leafOccur = false; // leaf 是否出现过
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode n = queue.poll();
            TreeNode l = n.left;
            TreeNode r = n.right;

            if (leafOccur) { // 出现过，则后面节点必须是叶结点
                if (l != null || r != null) { // 左右孩子存在，不是叶节点，则不是完全二叉树
                    return false;
                }
            }

            if (l == null && r != null) { // 出现无左孩子，但有右孩子的节点，则不是完全二叉树
                return false;
            }

            if (l == null && r == null) {
                leafOccur = true;
            }

            if (l != null)
                queue.add(l);
            if (r != null)
                queue.add(r);
        }
        return true;
    }

}
