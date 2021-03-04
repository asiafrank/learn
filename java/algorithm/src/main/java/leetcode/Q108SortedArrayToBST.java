package leetcode;

/**
 * 108. 将有序数组转换为二叉搜索树
 * https://leetcode-cn.com/problems/convert-sorted-array-to-binary-search-tree/
 * <p>
 * 给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵 高度平衡 二叉搜索树。
 * 高度平衡 二叉树是一棵满足「每个节点的左右两个子树的高度差的绝对值不超过 1 」的二叉树。
 * <p>
 * 因为数组有序，二叉搜索树中的中序遍历也有序。
 * 所以，将数组中间元素作为根结点，递归组成二叉搜索树即可。
 * 由于一直从中点开始，所以组成的二叉树是平衡的。
 *
 * @author zhangxiaofan 2021/03/04-09:37
 */
public class Q108SortedArrayToBST {

    /**
     * 主方法
     * @param nums 数组
     * @return 二叉树根结点
     */
    public static TreeNode sortedArrayToBST(int[] nums) {
        return buildTree(nums, 0, nums.length - 1);
    }

    /**
     * 递归将数组转换成树
     * @param nums 数组
     * @param left  开始下标
     * @param right 结束下标
     * @return 根结点
     */
    private static TreeNode buildTree(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }

        int mid = (left + right + 1) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = buildTree(nums, left, mid - 1);
        root.right = buildTree(nums, mid + 1, right);
        return root;
    }

    public static void main(String[] args) {
        int[] nums = new int[] {-10,-3,0,5,9};
        TreeNode root = sortedArrayToBST(nums);
        printTree(root); // -10,-3,0,5,9
    }

    // 中序遍历打印
    public static void printTree(TreeNode root) {
        if (root == null)
            return;

        printTree(root.left);
        System.out.print(root.val + ",");
        printTree(root.right);
    }

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
}
