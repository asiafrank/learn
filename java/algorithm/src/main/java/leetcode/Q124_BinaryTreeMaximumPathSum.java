package leetcode;

/**
 * 二叉树中的最大路径和
 * https://leetcode-cn.com/problems/binary-tree-maximum-path-sum/
 * <p>
 * hard
 * 提示：后序遍历
 */
public class Q124_BinaryTreeMaximumPathSum {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    private int answer = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        answer = Integer.MIN_VALUE;
        int oneSideMax = oneSideMaxSum(root);
        return Math.max(oneSideMax, answer);
    }

    /**
     * 后序遍历，返回 parent 下面单边的路径最大值
     */
    public int oneSideMaxSum(TreeNode parent) {
        if (parent == null)
            return 0;

        TreeNode left = parent.left;
        TreeNode right = parent.right;

        int leftSum = oneSideMaxSum(left);
        int rightSum = oneSideMaxSum(right);

        // 消除子树负值的影响
        leftSum = Math.max(0, leftSum);
        rightSum = Math.max(0, rightSum);

        // parent + left + right 连接之后是否是最大的(消除子树负值的影响)
        int linkSum = leftSum + rightSum + parent.val;
        answer = Math.max(answer, linkSum);

        int oneSideMax = Math.max(leftSum, rightSum);
        return oneSideMax + parent.val; // 最大单边连续路径
    }

    public static void main(String[] args) {
        Q124_BinaryTreeMaximumPathSum q = new Q124_BinaryTreeMaximumPathSum();

        TreeNode case1 = case1();
        int case1Sum = q.maxPathSum(case1);
        System.out.println("case1Sum: " + case1Sum);

        TreeNode case2 = case2();
        int case2Sum = q.maxPathSum(case2);
        System.out.println("case2Sum: " + case2Sum);

        TreeNode case3 = case3();
        int case3Sum = q.maxPathSum(case3);
        System.out.println("case3Sum: " + case3Sum);
    }

    /*
    -1
    / \
   -1 null
     */
    private static TreeNode case1() {
        TreeNode left = new TreeNode(-1);
        return new TreeNode(-1, left, null);
    }

    /*
       -1
      /  \
     -1  1
     */
    private static TreeNode case2() {
        TreeNode left = new TreeNode(-1);
        TreeNode right = new TreeNode(1);
        return new TreeNode(-1, left, right);
    }

    /*
      -1
    /    \
  null    9
         /  \
       -6    3
            /  \
          null  -2
     */
    private static TreeNode case3() {
        TreeNode root = new TreeNode(-1);
        TreeNode r9 = new TreeNode(9);
        root.right = r9;

        TreeNode l_6 = new TreeNode(-6);
        TreeNode r3 = new TreeNode(3);
        r9.left = l_6;
        r9.right = r3;

        TreeNode r_2 = new TreeNode(-2);
        r3.right = r_2;
        return root;
    }
}
