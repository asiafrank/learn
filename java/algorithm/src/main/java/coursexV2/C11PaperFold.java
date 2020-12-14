package coursexV2;

import com.asiafrank.TreeNode;

/**
 * 折纸痕问题：
 * 请把一段纸条竖着放在桌子上，然后从纸条的下边向上方对折1次，压出折痕后展开。此时折痕是凹下去的，即折痕突起的方向指向纸条的背面。 如果从纸条的下边向上方连续对折2次，压出折痕后展开，此时有三条折痕，从上到下依次是下折痕、下折痕和上折痕。
 * 给定一个输入参数N，代表纸条都从下边向上方连续对折N次。 请从上到下打印所有折痕的方向。
 * 例如:N=1时，打印: down N=2时，打印: down down up
 * 每次都基于上一次折痕，上方是凹折痕，下方是凸折痕
 * 从上往下打印，本质上是中序遍历。N 相当于树的高度
 *
 * 每次都基于上一次折痕，上方是凹折痕，下方是凸折痕
 * 从上往下打印，本质上是中序遍历。N 相当于树的高度
 * 左节点是凹，右节点是凸
 */
public class C11PaperFold {

    /**
     * 打印
     * @param N 树的层次，折几次
     */
    public static void printAllFolds(int N) {
        print(1, N, true); // root 节点是凹
    }

    /**
     * 当前你来到一个节点，脑海中想象
     * 这个节点在第i层，一共有N层，N固定不变
     * 这个节点如果是凹得话，down = true
     * 这个节点如果是凸得话，down = false
     * @param level 当前是多少层（从1开始）
     * @param N    总共多少层
     * @param down true, 凹；false，凸
     */
    public static void print(int level, int N, boolean down) {
        if (level > N) {
            return;
        }

        print(level + 1, N, true); // 右节点是 凸
        System.out.print(down ? "凹," : "凸,");
        print(level + 1, N, false); // 左节点是 凹
    }

    public static void main(String[] args) {
        printAllFolds(3);
    }
}
