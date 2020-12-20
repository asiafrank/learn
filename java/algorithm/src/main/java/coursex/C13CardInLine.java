package coursex;

/**
 * 范围上尝试的模型
 *
 * 给定一个整型数组arr，代表数值不同的纸牌排成一条线，
 * 玩家A和玩家B依次拿走每张纸牌，
 * 规定玩家A先拿，玩家B后拿，
 * 但是每个玩家每次只能拿走最左或最右的纸牌，
 * 玩家A和玩家B都绝顶聪明。请返回最后获胜者的分数。
 */
public class C13CardInLine {

    /**
     * cards 中 [l,r] 给玩家选择，求赢的玩家获得的分数
     * @param cards 纸牌数组
     * @return 赢的玩家获得的分数
     */
    public static int cardInLine(int[] cards) {
        int l = 0, r = cards.length - 1;
        return Math.max(firstChoose(cards, l, r), secondChoose(cards, l, r));
    }

    /**
     * 先手玩家选择，获得的最大分数
     * @param cards 纸牌数组
     * @param l     左闭区间
     * @param r     右闭区间
     * @return 先手玩家获得的最大分数
     */
    private static int firstChoose(int[] cards, int l, int r) {
        if (l == r) {
            return cards[l]; // 只剩一个，没得选择
        }

        int chooseLeft = cards[l] + secondChoose(cards, l + 1, r);
        int chooseRight = cards[r] + secondChoose(cards, l, r - 1);
        return Math.max(chooseLeft, chooseRight);
    }

    /**
     * 后手玩家选择，返回的是留给先手玩家的最小分数
     * @param cards 纸牌数组
     * @param l     左闭区间
     * @param r     右闭区间
     * @return 留给先手玩家的最小分数
     */
    private static int secondChoose(int[] cards, int l, int r) {
        if (l == r) {
            return 0; // 后手选择 cards[l], 留给先手玩家就是 0
        }
        // 后手玩家选了左侧/右侧，然后让先手玩家选，两者取最小值，就是留给先手玩家的分数
        int chooseLeft = firstChoose(cards, l + 1, r);
        int chooseRight = firstChoose(cards, l, r - 1);
        return Math.min(chooseLeft, chooseRight);
    }

    // TODO: dp做法

    public static void main(String[] args) {
        int[] cards = new int[] {1, 100, 4};
        int winMaxValue = cardInLine(cards);
        System.out.println(winMaxValue);
    }
}
