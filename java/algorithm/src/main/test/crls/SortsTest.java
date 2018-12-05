package crls;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * @author zhangxf created at 12/5/2018.
 */
public class SortsTest {

    @Test
    public void insertSortTest() {
        assertEquals(1, 1);

        int[] a = {7,6,5,4,3,2,1};
        Sorts.insertSort(a);

        int[] expect = {1,2,3,4,5,6,7};
        assertArrayEquals(expect, a);
    }
}
