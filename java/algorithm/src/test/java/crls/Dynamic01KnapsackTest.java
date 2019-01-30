package crls;

import crls.greedy.Dynamic01Knapsack;
import org.junit.jupiter.api.Test;

/**
 * @author zhangxf created at 12/5/2018.
 */
public class Dynamic01KnapsackTest {

    // 01 背包
    @Test
    public void test() {
        //         0  1  2  3  4  5  6
        int[] w = {0, 2, 4, 5, 6, 7, 9};
        int[] v = {0, 10, 4, 4, 3, 2, 10};
        int maxW = 10;
        Dynamic01Knapsack.D01KnapsackPair pair = Dynamic01Knapsack.solution(w, v, maxW);
        Dynamic01Knapsack.printD01Knapsack(pair.b, w, w.length - 1, maxW);
        System.out.println();
        Tools.printArray(pair.r);
        Tools.printArray(pair.b);
    }
}
