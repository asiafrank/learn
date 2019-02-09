package tooffer;

/**
 * 2.3.4节-树
 */
public class Trees {

    public static class Node {
        int value;
        Node left;
        Node right;
    }

    /*
    7: 重建二叉树
    输入某二叉树的先序遍历和中序遍历的结果，请重建该二叉树。
    假设输入的先序遍历的结果中都不含重复的数字。例如，输入
    先序遍历序列 {1,2,4,7,3,5,6,8} 和中序遍历序列
    {4,7,2,1,5,3,8,6}，则重建如图 2.6 所示的二叉树
    并输出它的头节点。二叉树节点定义见 class Node

    图 2.6：
          1
         / \
        2   3
       /   / \
      4   5   6
       \      /
        7    8
     */

    /**
     *
     * <pre>
     * preOrder: 1  2  4  7  3  5  6  8
     *           |  \-----/  \--------/
     *           根  左子树    右子树
     * inOrder:  4  7  2  1  5  3  8  6
     *           \-----/  |  \--------/
     *            左子树   根   右子树
     * </pre>
     * @param preOrder 先序遍历序列
     * @param inOrder  中序遍历序列
     * @return 根节点
     */
    public static Node rebuildTree(int[] preOrder, int[] inOrder) {
        if (preOrder == null || inOrder == null
                || preOrder.length <= 0 || inOrder.length <=0)
            return null;

        return build(preOrder, 0, preOrder.length - 1, inOrder, 0, inOrder.length - 1);
    }

    /**
     *
     * @param preOrder      先序遍历序列
     * @param preOrderStart 先序遍历开始元素下标
     * @param preOrderEnd   先序遍历结束元素下标
     * @param inOrder       中序遍历序列
     * @param inOrderStart  中序遍历开始元素下标
     * @param inOrderEnd    中序遍历结束元素下标
     * @return 当前处理好的节点
     */
    private static Node build(int[] preOrder, int preOrderStart, int preOrderEnd, int[] inOrder, int inOrderStart, int inOrderEnd) {
        if (preOrderStart >= preOrder.length)
            return null;

        Node root = new Node();
        root.value = preOrder[preOrderStart];

        int inOrderRootI = 0;
        for (int i = inOrderStart; i <= inOrderEnd; i++) {
            if (root.value == inOrder[i]) {
                inOrderRootI = i;
                break;
            }
        }

        int leftInOrderLength = inOrderRootI - inOrderStart;
        int leftPreOrderEnd = preOrderStart + leftInOrderLength; // 先序遍历中左子树的结束下标
        if (leftInOrderLength > 0)
            root.left = build(preOrder, preOrderStart + 1, leftPreOrderEnd, inOrder, inOrderStart, inOrderRootI - 1);

        if (leftInOrderLength < preOrderEnd - preOrderStart) // 左子树元素个数小于先序遍历中的元素个数，则代表剩下的元素都是右子树元素
            root.right = build(preOrder, leftPreOrderEnd + 1, preOrderEnd, inOrder, inOrderRootI + 1, inOrderEnd);
        return root;
    }
}
