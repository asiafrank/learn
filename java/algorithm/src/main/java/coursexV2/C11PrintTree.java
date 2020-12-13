package coursexV2;


import com.asiafrank.TreeNode;

/**
 * 打印整棵树
 */
public class C11PrintTree {

    public void printTree(TreeNode root) {
        printTree0(root, 0);
    }

    private void printTree0(TreeNode n, int level) {
        if (n == null)
            return;

        printTree0(n.right, level + 1);
        int l = level;
        StringBuilder prefix = new StringBuilder();
        while (l > 0) {
            prefix.append("  ");
            l--;
        }
        System.out.println(prefix.toString() + n.val);
        printTree0(n.left, level + 1);
    }

    public static void main(String[] args) {
        TreeNode n = new TreeNode(1);
        n.left = new TreeNode(1);
        n.right = new TreeNode(1);
        n.left.left = new TreeNode(2);
        n.left.right = new TreeNode(3);
        n.right.left = new TreeNode(5);
        n.right.right = new TreeNode(6);
        C11PrintTree c = new C11PrintTree();
        c.printTree(n);
    }
}
