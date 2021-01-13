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

        // TODO: 去重问题
        for (int i = 2; i < nums.length; i++) {
            int pick = nums[i]; // 1. 选定一个数

            // 2. 求二元组
            int l = 0;
            int r = i - 1;
            int target = -pick;
            while (l != r) {
                int sum = nums[l] + nums[r];
                if (sum > target) {
                    r--;
                } else if (sum < target) { // 证明太小了，l++
                    l++;
                } else if (sum == target) { // 收集答案
                    list.add(Arrays.asList(nums[l], nums[r], pick));
                    break;
                }
            }
        }
        return list;
    }


    public static void main(String[] args) {
        int[] nums = new int[] {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> list = threeSum(nums);
        System.out.println(list);
    }
}
