package labuladong.dp;

import java.util.Arrays;

/**
 * 最长递增子序列(Longest Increasing Subsequence LIS)
 * <p>
 * 给一个无序的整数数组，求这个数组的最长上升子序列
 * 如：[10,9,2,5,3,7,101,18]
 * 它的最长上升子序列是 [2,3,7,101]，长度为 4
 *
 * @author zhangxiaofan 2020/12/16-10:15
 */
public class C01LongestIncreasingSubsequence {

    // O(n^2)
    public static int lis(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;

        // dp[i] = x 的含义是以下标 i 元素为结尾的最长上升子序列长度为 x
        //           也就是说  nums[i] 包含在最长上升子序列里时，dp[i] = x
        //                    否则 dp[i] = 1
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1); // 默认都是 1
        for (int i = 1; i < nums.length; i++) { // i 从第一个元素开始
            for (int j = 0; j < i; j++) {      // j 0 到 i - 1 遍历，找到 dp[j] 求最大值
                if (nums[i] > nums[j]) { // 递增, 长度+1，找大值
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                }
            }
        }

        // 每个元素为结尾的LIS已经算好；现在只需找出 dp 里的最大值即可
        int max = 0;
        for (int e : dp) {
            max = Math.max(max, e);
        }
        return max;
    }

    /**
     * 二分查找解法, patience game 的纸牌排序，patience sorting
     *  O(nlog(n))
     * 只能把点数⼩的牌压到点数⽐它⼤的牌上。
     * 如果当前牌点数较⼤没有可以放 置的堆，则新建⼀个堆，把这张牌放进去。
     * 如果当前牌有多个堆可供选择， 则选择最左边的堆放置。
     *
     * @param nums 牌组
     * @return 牌堆个数，即最大上升子序列长度
     */
    public static int lengthOfLIS(int[] nums) {
        int[] top = new int[nums.length]; // 牌堆放置的地方, 每个元素是堆顶牌大小
        int piles = 0; // 牌堆数量设置 0

        for (int i = 0; i < nums.length; i++) {
            int poker = nums[i]; // 牌
            // 二分查找应该放置到哪个牌堆, 左边界二分查找
            int left = 0, right = piles;
            while (left < right) {
                int mid = (left + right) / 2;
                if (top[mid] < poker) {
                    right = mid;
                } else if (top[mid] > poker) {
                    left = mid + 1;
                } else if (top[mid] == poker) { // 相等
                    right = mid;
                }
            }

            // 没找到合适的牌堆，新建⼀堆
            if (left == piles) piles++;
            // 把这张牌放到牌堆顶
            top[left] = poker;
        }
        return piles;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{10, 9, 2, 5, 3, 7, 101, 18};
        int lis = lis(nums);
        System.out.println(lis);

        int lis2 = lengthOfLIS(nums);
        System.out.println(lis2);
    }
}
