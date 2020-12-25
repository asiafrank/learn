package leetcode;

import com.asiafrank.util.Printer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 两数之和
 * https://leetcode-cn.com/problems/two-sum/
 * easy
 *
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 *
 * 示例:
 *
 * 给定 nums = [2, 7, 11, 15], target = 9
 *
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 */
public class Q1_TwoSum {
    public static int[] twoSum(int[] nums, int target) {
        // key: num, value: index
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int n = nums[i];
            int delta = target - n;
            Integer d = map.get(delta);
            if (Objects.nonNull(d)) {
                return new int[] {d, i};
            }
            map.put(n, i);
        }
        return new int[] {-1, -1}; // -1 代表没找到
    }

    public static void main(String[] args) {
        int[] nums = new int[] {2, 7, 11, 15};
        int[] rs = twoSum(nums, 9);
        Printer.printArray(rs); //[0, 1]
    }
}