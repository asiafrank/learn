package crls;

import crls.dp.LongestPalindromeSequence;
import org.junit.jupiter.api.Test;

/**
 * @author zhangxf created at 12/5/2018.
 */
public class DPTest {

    // 矩阵链乘法括号化方案
    @Test
    public void matrixChainOrderTest() {
        int[] p = {5, 10, 3, 12, 5, 50, 6};
        DP.Pair pair = DP.matrixChainOrder(p);
        DP.printOptimalParens(pair.s, 1, pair.s.length);
        Tools.printArray(pair.m);
        System.out.println();
        Tools.printArray(pair.s);
    }

    // 最长公共子序列
    @Test
    public void lcsLengthTest() {
        int[] x = {'A', 'B', 'C', 'B', 'D', 'A', 'B'};
        int[] y = {'B', 'D', 'C', 'A', 'B', 'A'};
        DP.LCSPair pair = DP.lcsLength(x, y);
        Tools.printArray(pair.c);
        Tools.printArray(pair.b);
        DP.printLCS(pair.b, x, x.length - 1, y.length - 1);
        System.out.println();
    }

    /**
     * 有向无环图中的最长简单路径
     *
     * 图如下, 其中 0-3 的最长简单路径是 0,1,2,3 权重和为 6
     * <pre>
     *              [4]
     *              /|\
     *               | 1
     *         2     |   3          1
     *    [0]------>[1]------->[2]------->[3]
     *               |                    /|\
     *               |---------------------|
     *                       2
     * </pre>
     */
    @Test
    public void lspTest() {
        // 结点编号 0-4
        int[] v = {0, 1, 2, 3, 4};
        DP.Node[] adj = new DP.Node[v.length];

        // 边 0-1
        DP.Node n0 = new DP.Node(0);
        DP.Node n0_1 = new DP.Node(1);
        n0.next = n0_1;
        n0_1.weight = 2;
        adj[0] = n0;

        // 边 1-4
        DP.Node n1 = new DP.Node(1);
        DP.Node n1_4 = new DP.Node(4);
        n1.next = n1_4;
        n1_4.weight = 1;
        // 边 1-2
        DP.Node n1_2 = new DP.Node(2);
        n1_4.next = n1_2;
        n1_2.weight = 3;
        // 边 1-3
        DP.Node n1_3 = new DP.Node(3);
        n1_2.next = n1_3;
        n1_3.weight = 2;
        adj[1] = n1;

        // 边 2-3
        DP.Node n2 = new DP.Node(2);
        DP.Node n2_3 = new DP.Node(3);
        n2.next = n2_3;
        n2_3.weight = 1;
        adj[2] = n2;

        DP.Node n3 = new DP.Node(3);
        adj[3] = n3;
        DP.Node n4 = new DP.Node(4);
        adj[4] = n4;

        DP.LSPPair lspPair = DP.lsp(v, adj, 0, 3);

        Tools.printArray(lspPair.dist);
        System.out.println();
        System.out.print(0 + " ");
        for (int n : lspPair.p) {
            System.out.print(n + " ");
        }
        System.out.println();
    }

    @Test
    public void longestPalindromeSequenceTest() {
        String s = "civic";
        char[] x = s.toCharArray();
        LongestPalindromeSequence.LPSPair pair = LongestPalindromeSequence.solution(x);
        Tools.printArray(pair.c);
        System.out.println();
        LongestPalindromeSequence.printLSP(x, pair.c, 0, x.length - 1);
    }
}
