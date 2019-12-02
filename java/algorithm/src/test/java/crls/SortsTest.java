package crls;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * @author zhangxf created at 12/5/2018.
 */
class SortsTest {

    @Test
    void insertSortTest() {
        assertEquals(1, 1);

        int[] a = {7,6,5,4,3,2,1};
        Sorts.insertSort1(a);

        int[] expect = {1,2,3,4,5,6,7};
        assertArrayEquals(expect, a);
    }

    @Test
    void mergeSortTest() {
        int[] a = {7,6,5,4,3,2,1};
        Sorts.mergeSort(a, 0, a.length - 1);

        int[] expect = {1,2,3,4,5,6,7};
        assertArrayEquals(expect, a);
    }
}
