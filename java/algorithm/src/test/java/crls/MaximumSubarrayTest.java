package crls;

import crls.divide.MaximumSubarray;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * @author zhangxf created at 2/11/2019.
 */
public class MaximumSubarrayTest {
    @Test
    public void insertSortTest() {
        int[] a = {13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7};
        int[] r = MaximumSubarray.solution(a);

        int[] expect = {18, 20, -7, 12};
        assertArrayEquals(expect, r);
    }
}
