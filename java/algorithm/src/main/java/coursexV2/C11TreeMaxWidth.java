package coursexV2;

import com.asiafrank.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 树的最大宽度
 */
public class C11TreeMaxWidth {

    /**
     * 宽度优先遍历
     *
     * curEnd 标记当前层结束的节点
     * nextEnd 下一层结束的节点
     * max 记录最大宽度
     * currLevelWidth 记录当前层宽度
     */
    public int countMaxWidth(TreeNode root) {
        if (root == null)
            return 0;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode curEnd = root;
        TreeNode nextEnd = null;
        int max = 1;
        int currLevelWidth = 0;

        while (!queue.isEmpty()) {
            TreeNode n = queue.poll();
            currLevelWidth++;
            TreeNode l = n.left;
            TreeNode r = n.right;
            if (l != null) {
                queue.add(l);
                nextEnd = l;
            }
            if (r != null) {
                queue.add(r);
                nextEnd = r;
            }
            if (n == curEnd) {
                max = Math.max(max, currLevelWidth);
                currLevelWidth = 0;
                curEnd = nextEnd;
                nextEnd = null;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        TreeNode n = new TreeNode(1);
        n.left = new TreeNode(1);
        n.right = new TreeNode(1);
        n.left.left = new TreeNode(2);
        n.left.right = new TreeNode(3);
        n.right.left = new TreeNode(5);
        n.right.right = new TreeNode(6);
        C11TreeMaxWidth c = new C11TreeMaxWidth();
        int max = c.countMaxWidth(n);
        System.out.println(max);
    }
}
