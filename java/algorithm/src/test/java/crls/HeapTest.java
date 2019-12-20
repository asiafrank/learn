package crls;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 堆测试
 *
 * @author zhangxiaofan 2019/12/20-16:28
 */
public class HeapTest {

    @Test
    public void test() {
        int[] a = {27, 17, 3, 16, 13, 10, 1, 5, 7, 12, 4, 8, 9, 0};
        Heap.heapSort(a);

        int[] expected = {0, 1, 3, 4, 5, 7, 8, 9, 10, 12, 13, 16, 17, 27};
        Assertions.assertArrayEquals(expected, a);
    }
}