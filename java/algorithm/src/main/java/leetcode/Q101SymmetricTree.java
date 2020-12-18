package leetcode;

import com.asiafrank.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 101 对称二叉树
 *
 * 例如，二叉树[1,2,2,3,4,4,3] 是对称的。
 *
 *     1
 *    / \
 *   2   2
 *  / \ / \
 * 3  4 4  3
 *
 *
 * 但是下面这个[1,2,2,null,3,null,3] 则不是镜像对称的:
 *
 *     1
 *    / \
 *   2   2
 *    \   \
 *    3    3
 */
public class Q101SymmetricTree {

    /**
     * 递归
     * 每个节点只会遍历一次，所以 O(n)
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null)
            return true;

        TreeNode left = root.left;
        TreeNode right = root.right;
        return process(left, right);
    }

    private boolean process(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        } else if (left != null && right != null) {
            if (left.val != right.val) {
                return false;
            }

            return process(left.left, right.right) && process(left.right, right.left);
        }
        return false;
    }

    /**
     * 首先我们引入一个队列，这是把递归程序改写成迭代程序的常用方法。
     * 初始化时我们把根节点入队两次。
     * 每次提取两个结点并比较它们的值（队列中每两个连续的结点应该是相等的，而且它们的子树互为镜像），
     * 然后将两个结点的左右子结点按相反的顺序插入队列中。
     * 当队列为空时，或者我们检测到树不对称（即从队列中取出两个不相等的连续结点）时，该算法结束。
     */
    public boolean isSymmetric2(TreeNode root) {
        if (root == null)
            return true;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.push(root);
        queue.push(root);

        while (!queue.isEmpty()) {
            TreeNode pop1 = queue.pop();
            TreeNode pop2 = queue.pop();

            if (pop1.val != pop2.val)
                return false;

            TreeNode left1 = pop1.left;
            TreeNode right1 = pop1.right;
            TreeNode left2 = pop2.left;
            TreeNode right2 = pop2.right;

            if (left1 != null && right2 != null) {
                queue.push(left1);
                queue.push(right2);
            } else if (left1 != right2) {
                return false;
            }

            if (right1 != null && left2 != null) {
                queue.push(right1);
                queue.push(left2);
            } else if (right1 != left2) {
                return false;
            }
        }
        return true;
    }
}
