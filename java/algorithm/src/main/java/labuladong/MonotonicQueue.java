package labuladong;

import java.util.*;

/**
 * 给定一个数组 nums，有一个大小为 k 的滑动窗口，从数组的最左侧，
 * 移动到数组的最右侧。你只可以看到滑动窗口 k 内的数字。
 * 滑动窗口每次只向右滑动一位。
 * <p>
 * 返回滑动窗口的最大值.
 * <p>
 * 使用单调队列做法:
 * 保证队列里，队头是窗口最大值；并且队列内元素单调递减。
 * 滑动窗口进入一个值 x，push 时，将队列里小于等于 x 的都剔除
 *
 * @author zhangxiaofan 2020/12/14-14:56
 */
public class MonotonicQueue {
    private Deque<Integer> queue = new ArrayDeque<>();

    /**
     * 将元素 x 塞进队列
     */
    public void push(int x) {
        while (!queue.isEmpty() && queue.peekFirst() <= x) {
            queue.pollFirst();
        }
        queue.addLast(x);
    }

    /**
     * 返回队列最大值
     */
    public Integer max() {
        return queue.peekFirst();
    }

    /**
     * 弹出元素 x
     */
    public void pop(int x) {
        if (!queue.isEmpty() && queue.peekFirst() == x) {
            queue.pollFirst();
        }
    }

    /**
     * @param nums 数据
     * @param k    窗口大小
     * @return 每次窗口的最大值
     */
    public static List<Integer> maxSlideWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return Collections.emptyList();
        }

        List<Integer> result = new ArrayList<>();
        MonotonicQueue q = new MonotonicQueue();
        int i = 0;
        for (; i < k; i++) { // 先把窗口塞满
            q.push(nums[i]);
        }

        for (; i < nums.length; i++) {
            result.add(q.max());

            int curr = nums[i];
            int windowFirst = nums[i - k]; // 窗口第一个元素
            q.pop(windowFirst);
            q.push(curr);
        }
        result.add(q.max()); // 最后一个元素进去后，再取一次
        return result;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 3, -1, -3, 5, 3, 6, 7};
        List<Integer> result = maxSlideWindow(nums, 3);
        for (Integer i : result) {
            System.out.print(i + ",");
        }
    }
}
