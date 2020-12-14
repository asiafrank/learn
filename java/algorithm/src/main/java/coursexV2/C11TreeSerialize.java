package coursexV2;

import com.asiafrank.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 树的序列化
 *
 * 中序遍历序列化，有歧义，所以不可行
 * 如：
 *     2              1
 *    / \            / \
 *   1   nil        nil 2
 *  / \                / \
 * nil nil            nil nil
 * 上面两棵树，中序遍历顺序都一样：[nil,1,nil,2,nil]
 */
public class C11TreeSerialize {

    /**
     * 先序遍历序列化
     * @return 序列化后的顺序
     */
    public Queue<String> preSerial(TreeNode root) {
        Queue<String> queue = new LinkedList<>();
        preSerial0(root, queue);
        return queue;
    }

    private void preSerial0(TreeNode root, Queue<String> queue) {
        if (root == null) {
            queue.add("null");
        } else {
            queue.add(String.valueOf(root.val));
            preSerial0(root.left, queue);
            preSerial0(root.right, queue);
        }
    }

    /**
     * 反序列化
     * @return 反序列化后的 root 节点
     */
    public TreeNode preDeserial(Queue<String> queue) {
        if (queue.isEmpty())
            return null;

        String s = queue.poll();
        if (s.equals("null"))
            return null;

        TreeNode n = new TreeNode(Integer.parseInt(s));
        n.left = preDeserial(queue);
        n.right = preDeserial(queue);
        return n;
    }

    public void printPreOrder(TreeNode root) {
        if (root == null) {
            System.out.print("null,");
            return;
        }

        System.out.print(root.val + ",");
        printPreOrder(root.left);
        printPreOrder(root.right);
    }

    public static void main(String[] args) {
        C11TreeSerialize c = new C11TreeSerialize();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);

        root.right.right = new TreeNode(4);
        Queue<String> q = c.preSerial(root);
        for (String s : q) {
            System.out.print(s + ",");
        }

        System.out.println();
        TreeNode t = c.preDeserial(q);
        c.printPreOrder(t);
    }
}
