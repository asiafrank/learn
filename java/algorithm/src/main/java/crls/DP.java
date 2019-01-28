package crls;

import java.util.Objects;

/**
 * 动态规划-P204
 *
 * @author zhangxf created at 1/21/2019.
 */
public class DP {

    public static class Pair {
        public Pair(int[][] m, int[][] s) {
            this.m = m;
            this.s = s;
        }

        public int[][] m;
        public int[][] s;
    }

    //---------------------------------------
    // 矩阵链乘法
    //---------------------------------------

    /**
     * 矩阵链乘法
     *
     * @param p 矩阵规模序列
     * @return 计算结果 m 和 划分点 s
     */
    public static Pair matrixChainOrder(int[] p) {
        int n = p.length - 1;
        //  m[1..n][1..n]
        int[][] m = new int[n+1][n+1];
        //  s[1..n-1][2..n]
        int[][] s = new int[n][n+1];
        for (int i = 0; i <= n; i++)
            m[i][i] = 0;

        for (int l = 2; l <= n; l++) {
            int end = n - l + 1;
            for (int i = 1; i <= end; i++) {
                int j = i + l - 1;
                m[i][j] = Integer.MAX_VALUE;
                for (int k = i; k <= j - 1; k++) {
                    int q = m[i][k] + m[k+1][j] + p[i-1] * p[k] * p[j];
                    if (q < m[i][j]) {
                        m[i][j] = q;
                        s[i][j] = k;
                    }
                }
            }
        }

        return new Pair(m, s);
    }

    public static void printOptimalParens(int[][] s, int i, int j) {
        if (i == j)
            System.out.print("A"+ i);
        else {
            System.out.print("(");
            printOptimalParens(s, i, s[i][j]);
            printOptimalParens(s, s[i][j]+1, j);
            System.out.print(")");
        }
    }

    //---------------------------------------
    // 公共最长子序列
    //---------------------------------------

    public static class LCSPair {
        public LCSPair(char[][] b, int[][] c) {
            this.b = b;
            this.c = c;
        }

        char[][] b;
        int[][] c;
    }

    public static LCSPair lcsLength(int[] x, int[] y) {
        int m = x.length - 1;
        int n = y.length - 1;
        char[][] b = new char[m+1][n+1]; // 帮助构造最优解的路径表
        int[][] c = new int[m+1][n+1]; // 对于 x[i] y[j] 的最长公共子序列长度为 c[i][j]

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (x[i] == y[j]) {
                    c[i][j] = c(c, i-1, j-1) + 1;
                    b[i][j] = '↖';
                } else if (c(c, i-1, j) >= c(c, i, j-1)) { // z[k]!=x[i] z[k]=y[j] 的情况
                    c[i][j] = c(c, i-1, j);
                    b[i][j] = '↑';
                } else { // z[k]=x[i] z[k]!=y[j] 的情况
                    c[i][j] = c(c, i, j-1);
                    b[i][j] = '←';
                }
            }
        }
        return new LCSPair(b, c);
    }

    /**
     * 取消算法导论伪代码中对 c[i][0] 和 c[0][j] 的初始化，并且传入的 x，y 数组下表从 0 开始
     */
    private static int c(int[][] c, int i, int j) {
        if (i < 0 || j < 0)
            return 0;
        return c[i][j];
    }

    public static void printLCS(char[][] b, int[] x, int i, int j) {
        if (i < 0 || j < 0)
            return;

        if (b[i][j] == '↖') {
            printLCS(b, x, i - 1, j - 1);
            System.out.print(x[i]);
        } else if (b[i][j] == '↑') {
            printLCS(b, x, i - 1, j);
        } else {
            printLCS(b, x, i, j - 1);
        }
    }

    //-----------------------------------------------------
    // 思考题 15-1 有向无环图中的最长简单路径. 结点编号都从零开始
    //-----------------------------------------------------

    /**
     * 图的邻接链表表示法中的一个结点
     */
    public static class Node {
        /**
         * 指向下一个结点，如果到链尾，则下一个结点为 null
         */
        Node next;

        /**
         * 当前结点的编号，只要编号一样，就是同一个结点。
         * 注意：由于邻接链表法，一个编号可能有多个 Node 实例。
         */
        int num;

        /**
         * 当前边的权重
         */
        int weight;

        public Node(int num) {
            this.num = num;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return num == node.num;
        }

        @Override
        public int hashCode() {
            return Objects.hash(num);
        }
    }

    // Longest-Simple-Path Pair
    public static class LSPPair {
        /**
         * dist[1][2] 表示结点 1 到结点 2 的最长简单路径的权重和
         */
        int[][] dist;

        /**
         * 求得最长简单路径上各个结点的编号。不包括第一个结点
         */
        int[] p;
    }

    /**
     * 求有向无环图中的最长简单路径
     *
     * @param nodes 结点编号列表
     * @param adj   邻接链表
     * @param u     从 u 结点开始
     * @param v     到 v 结点结束
     * @return 结果
     */
    public static LSPPair lsp(int[] nodes, Node[] adj, int u, int v) {
        LSPPair lspPair = new LSPPair();
        lspPair.dist = new int[nodes.length][nodes.length];
        lspPair.p = new int[nodes.length];

        findLSP(lspPair.dist, lspPair.p, 0, adj, u, v);
        return lspPair;
    }

    /**
     * 求有向无环图中的最长简单路径
     *
     * @param dist 最长简单路径权重和记录表
     * @param p    最长简单路径结点编号记录表
     * @param i    p路径上第几个结点
     * @param adj  邻接链表
     * @param u    开始结点编号
     * @param v    结束结点编号
     * @return u 到 v 的最长简单路径的权重和
     */
    private static int findLSP(int[][] dist, int[] p, int i, Node[] adj, int u, int v) {
        if (u == v) {
            return 0;
        }

        Node x = adj[u].next; // x 为代表 u 的 Node 实例的下一个结点\
        int q;
        while (x != null) {
            q = findLSP(dist, p, i+1, adj, x.num, v) + x.weight;
            if (q > dist[u][v]) {
                dist[u][v] = q;
                p[i] = x.num;
            }
            x = x.next;
        }
        return dist[u][v];
    }
}
