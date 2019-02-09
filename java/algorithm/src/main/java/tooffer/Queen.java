package tooffer;

/**
 * P200
 * 八皇后问题，是一个古老而著名的问题，是回溯算法的典型案例。该问题是国际西洋棋棋手马克斯·贝瑟尔于1848年提出：
 * 在8×8格的国际象棋上摆放八个皇后，使其不能互相攻击，即任意两个皇后都不能处于同一行、同一列或同一斜线上，问有多少种摆法。
 * 高斯认为有76种方案。1854年在柏林的象棋杂志上不同的作者发表了40种不同的解，后来有人用图论的方法解出92种结果。
 * 计算机发明后，有多种计算机语言可以解决此问题。
 */
public class Queen {
    private int[] column; //同列是否有皇后，1表示有
    private int[] rup;    //右上至左下是否有皇后
    private int[] lup;    //左上至右下是否有皇后
    private int[] queen;  //解答
    private int num;      //解答编号
 
    public Queen() {
        // 棋盘下标都从 1 开始，0 下标忽略
        column = new int[8+1];
        rup = new int[(2*8)-1]; // 15 条反对角线
        lup = new int[(2*8)-1]; // 15 条正对角线
        for (int i = 1; i <= 8; i++)
            column[i] = 0;
        for (int i = 0; i < (2*8)-1; i++)
            rup[i] = lup[i] = 0;  //初始定义全部无皇后
        queen = new int[8+1];
    }

    /**
     * 每次递归下去，i 行号加一。
     * if 判断时，只需检查列、斜线上有无皇后即可。
     * j 为列号，每次都从 1 开始
     *
     * 数组rup代表反对角线冲突，为rup[i+j-2]（行号加列号-2），反对角线也有15条，即从rup[0]~c[15]，如果某条反对角线上已经有皇后，则为1，否则为0；
     * 数组lup代表正对角线冲突，为lup[i-j+7]（行号-列号+7），正对角线共有15条，即从lup[0]~lup[15]，如果某条正对角线上已经有皇后，则为1，否则为0；
     *
     * rup[i+j-2] 代表 (i,j) 对应的反对角线
     * lup[i-j+7] 代表 (i,j) 对应的反独角线
     *
     * @param i 当前行编号，最初从 1 开始。
     */
    public void backtrack(int i) {
        if (i > 8) {
            showAnswer();
        } else {
            for (int j = 1; j <= 8; j++) {
                if ((column[j] == 0) && (rup[i+j-2] == 0) && (lup[i-j+7] == 0)) { // 由于 i 为行号，当前行必定为空，所以
                    //若无皇后                                                     // 只需判断列、斜线上有无皇后即可。
                    queen[i] = j; //设定为占用
                    column[j] = rup[i+j-2] = lup[i-j+7] = 1;
                    backtrack(i+1);  //循环调用
                    column[j] = rup[i+j-2] = lup[i-j+7] = 0;
                }
            }
        }
    }
 
    protected void showAnswer() {
        num++;
        System.out.println("\n解答" + num);
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                if(queen[y]==x) {
                    System.out.print("Q");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }
 
    public static void main(String[] args) {
        Queen queen = new Queen();
        queen.backtrack(1);
    }
}