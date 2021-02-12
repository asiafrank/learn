package leetcode;

import java.util.*;

/**
 * 三和问题
 * medium
 * 1. 先会求2元组
 *   - 排序
 *   - 左右指针收集答案
 * 2.选定一个数，然后求 2元组，组成的就是3元组答案
 * https://leetcode-cn.com/problems/3sum/
 */
public class Q15_3Sum {
    /**
     * 选定一个数，然后求二元组
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        if (nums == null || nums.length < 3)
            return list;

        Arrays.sort(nums);
        int target = 0;
        int end = nums.length - 1;

        for (int i = 0; i < nums.length; i++) {
            int curr = nums[i];
            int rest = target - curr;

            if (i > 0 && nums[i] == nums[i - 1]) {
                // 如果 nums[i] 与 nums[i-1] 与前一个数相等，
                // 则不用再次求解
                continue;
            }

            List<int[]> twoSum = twoSum(nums, i + 1, end, rest);
            for (int[] item : twoSum) {
                Integer a = item[0];
                Integer b = item[1];
                list.add(Arrays.asList(curr, a, b));
            }
        }
        return list;
    }

    /**
     * nums 已经有序
     */
    private static List<int[]> twoSum(int[] nums, int begin, int end, int target) {
        // 双指针
        List<int[]> result = new ArrayList<>();
        int l = begin;
        int r = end;
        while (l < r) {
            int left = nums[l];
            int right = nums[r];
            int rs = left + right;

            if (l > begin && left == nums[l - 1]) {
                // 如果 nums[l] 与 nums[l-1] 相等，那就没必要再找答案了
                // 避免重复答案的产生
                l++;
                continue;
            }

            if (rs > target) {
                r--;
            } else if (rs < target) {
                l++;
            } else if (rs == target) {
                l++;
                r--;
                // 收集答案
                result.add(new int[]{left, right});
            }
        }
        return result;
    }


    public static void main(String[] args) {
        int[] nums = new int[] {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> list = threeSum(nums);
        System.out.println(list);
    }
}
