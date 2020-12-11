package labuladong;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 单调栈
 * Next Greater Number
 *
 * 给你⼀个数组 [2,1,2,4,3]，你返回数组 [4,2,4,-1,-1]。
 *
 * 解释：
 * 第⼀个 2 后⾯⽐ 2 ⼤的数是 4;
 * 1 后⾯⽐ 1 ⼤的数是 2；
 * 第⼆个 2 后⾯ ⽐ 2 ⼤的数是 4;
 * 4 后⾯没有⽐ 4 ⼤的数，填 -1；
 * 3 后⾯没有⽐ 3 ⼤的数，填 -1。
 *
 * 暴力解：O(n^2) 遍历每一个元素，针对这个元素找 其后比它大的数
 * 单调栈：O(n) 从后往前遍历，针对每个元素，栈中小于等于该元素的都弹出，留下大的那个，就是这个元素的解
 *
 * @author zhangxiaofan 2020/12/11-10:25
 */
public class NextGreaterNumber {

    public static int[] nextGreaterNumber(int[] arr) {
        Deque<Integer> stack = new ArrayDeque<>();

        int[] ans = new int[arr.length];

        int i = arr.length - 1;
        while (i >= 0) {
            int cur = arr[i]; // 当前元素
            while (!stack.isEmpty() && stack.peek() <= cur) { // 矮的弹出
                stack.pop();
            }

            ans[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(cur);
            i--;
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{2,1,2,4,3};
        int[] ans = nextGreaterNumber(arr);
        for (int a : ans) { // [4,2,4,-1,-1]
            System.out.print(a + ",");
        }
    }
}
