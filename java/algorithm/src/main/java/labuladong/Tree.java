package labuladong;

import com.asiafrank.TreeNode;

/**
 * 二叉树遍历相关
 * @author zhangxiaofan 2020/12/10-10:26
 */
public class Tree {

    /**
     * 二叉树遍历，每个节点 val 加一
     * @param root 当前遍历节点
     */
    public static void plusOne(TreeNode root) {
        if (root == null)
            return;

        root.val += 1;
        plusOne(root.left);
        plusOne(root.right);
    }

    /**
     * 判断两棵树是否一模一样
     * @param root1 第一棵树
     * @param root2 第二棵树
     * @return true, 一样；false, 不一样
     */
    public static boolean isSameTree(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) // 都是 null， 就相等
            return true;

        if (root1 == null || root2 == null) // 只有一个是 null，则不相等
            return false;

        if (root1.val != root2.val)
            return false;

        return isSameTree(root1.left, root2.left)
                && isSameTree(root1.right, root2.right);
    }

    /**
     * 判断一棵树是否是二叉树。（二叉树的每个节点，大于左子树的所有节点，小于右子树的所有节点）
     *
     * @return true，是二叉树；false，不是二叉树
     */
    public static boolean isValidBST(TreeNode root, TreeNode min, TreeNode max) {
        if (root == null)
            return true;

        if (min != null && root.val <= min.val)
            return false;
        if (max != null && root.val >= max.val)
            return false;

        return isValidBST(root.left, min, root) // 左子树 必须 小于 root
                && isValidBST(root.right, root, max); // 右子树 必须 大于 root
    }

    /**
     * 判断一个数是否在BST中存在
     * @return true，存在；false，不存在
     */
    public static boolean isInBST(TreeNode root, Integer target) {
        if (root == null)
            return false;
        if (root.val == target)
            return true;

        if (root.val > target) // 如果当前值大于 target，则只有左子树可能存在
            return isInBST(root.left, target);
        return isInBST(root.right, target); // 否则，右子树可能存在
    }

    /**
     * 插入一个节点到 BST 中
     * @param root 当前比较的节点
     * @param val 需要插入的节点值
     */
    public static TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }

        if (root.val > val) { // 左子树里插入
            root.left = insertIntoBST(root.left, val);
        }

        if (root.val < val) { // 右子树里插入
            root.right = insertIntoBST(root.right, val);
        }
        return root;
    }

    /**
     * 删除一个节点
     * @param root 当前比较的节点
     * @param val  需要删除的值
     */
    public static TreeNode deleteNode(TreeNode root, int val) {
        if (root == null)
            return null;

        if (val == root.val) {
            // case 1: 当前节点无左右孩子，直接删除
            if (root.left == null && root.right == null)
                return null;

            // case 2: 有一个孩子，将孩子接上来即可
            if (root.left != null && root.right == null) {
                return root.left;
            } else if (root.right != null && root.left == null) {
                return root.right;
            }

            // case 3: 左右孩子都存在，则找到其后继节点（右子树最小节点来接替），
            TreeNode minNode = getMin(root.right);
            root.val = minNode.val; // 为了便利，这里直接赋值; 然后删除这个最小节点
            root.right = deleteNode(root.right, minNode.val);
            return root;
        } else if (val < root.val) {
            root.left = deleteNode(root.left, val);
        } else if (val > root.val) {
            root.right = deleteNode(root.right, val);
        }
        return root;
    }

    /**
     * 获取最小节点
     * 一直往左遍历，直到没有孩子
     */
    private static TreeNode getMin(TreeNode root) {
        TreeNode min = root;
        while (min.left != null) {
            min = min.left;
        }
        return min;
    }

    public static void main(String[] args) {
        plusOneTest();
    }

    private static void plusOneTest() {
        TreeNode n = new TreeNode(1);
        TreeNode l = new TreeNode(2);
        TreeNode r = new TreeNode(3);
        n.left = l;
        n.right = r;
        plusOne(n);
        printNodeVal(n);
    }

    public static void printNodeVal(TreeNode root) {
        if (root == null)
            return;

        System.out.print(root.val + "  ");
        printNodeVal(root.left);
        printNodeVal(root.right);
    }

}
