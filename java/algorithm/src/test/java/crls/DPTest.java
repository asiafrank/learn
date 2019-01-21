package crls;

import org.junit.jupiter.api.Test;

/**
 * @author zhangxf created at 12/5/2018.
 */
public class DPTest {

    @Test
    public void matrixChainOrderTest() {
        int[] p = {5, 10, 3, 12, 5, 50, 6};
        DP.Pair pair = DP.matrixChainOrder(p);
        DP.printOptimalParens(pair.s, 1, pair.s.length);
        printArray(pair.m);
        System.out.println();
        printArray(pair.s);
    }

    private void printArray(int[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }
}
