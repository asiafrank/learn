package tooffer;

import com.asiafrank.util.Queue;

/**
 * 数据流中的中位数
 *
 * 如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。
 * 如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。
 */
public class Q41 {

    private class DynamicArray {
        void insert(int i) {
            // TODO:
        }

        int getMedian() {
            // TODO:
            return 0;
        }
    }

    public static void solution(Queue<Integer> stream, DynamicArray arr) {
        // TODO:
        while (!stream.isEmpty()) {
            Integer i = stream.dequeue();
            arr.insert(i);
            System.out.println(arr.getMedian());
        }
    }
}
