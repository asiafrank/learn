package coursexV2;

/**
 * 获取给定节点 x 的后继节点
 *
 * 后继节点定义：中序遍历中 x 节点的下一个节点
 *
 * 分三种情况：
 * 1. 有右孩子，则取右孩子最左节点
 * 2. 无右孩子，本身是父结点的左孩子，则父结点就是后继
 * 3. 无右孩子，本身是父结点的右孩子，往上找，直到找到某个节点是其父节点的左孩子
 *
 * 2,3 可以合并
 */
public class C11Successor {

    private static class Node {
        int val;
        Node left;
        Node right;
        Node parent;
    }

    public Node findSuccessor(Node x) {
        if (x == null)
            return null;

        Node n = x.right;
        // case 1
        if (n != null) {
            Node p = n;
            while (n != null) {
                p = n;
                n = n.left;
            }
            return p;
        }

        // case 2,3
        n = x;
        Node p = n.parent;
        while (p != null && p.right == n) { // 当前节点是父结点的右孩子
            n = p;
            p = p.parent;
        }
        // 直到是父结点的左孩子，才会退出
        return p;
    }
}
