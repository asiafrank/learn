package leetcode;

/**
 * 213. 打家劫舍 II
 * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。
 * 这个地方所有的房屋都 围成一圈 ，这意味着第一个房屋和最后一个房屋是紧挨着的。
 * 同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，
 * 系统会自动报警 。
 *
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，
 * 能够偷窃到的最高金额。
 *
 * @author zhangxiaofan 2021/03/03-11:06
 */
public class Q213HouseRobber2 {

    /**
     * 数组 nums，
     * 如果第0元素抢，那么第 n-1 个元素就不能抢。（范围 0 ~ n-2）
     * 如果第0元素不抢，那么第 n-1 元素就能抢。（范围 1 ~ n-1）
     */
    public static int rob(int[] nums) {
        int n = nums.length;
        if (n == 1)
            return nums[0];

        return Math.max(
                robRange(nums, 0, n - 2), // 0抢，n-1不能抢
                robRange(nums, 1, n - 1)  // 1不抢，n-1能抢
        );
    }

    /**
     * 动态规划求打家劫舍
     *
     * dp[i] = max(dp[i + 1], arr[i] + dp[i + 2])
     */
    public static int robRange(int[] arr, int start, int end) {
        // base case
        int dp_i_2 = 0;
        int dp_i_1 = 0;
        int dp_i = 0;
        for (int i = end; i >= start; i--) {
            dp_i = Math.max(dp_i_1, arr[i] + dp_i_2);
            dp_i_2 = dp_i_1;
            dp_i_1 = dp_i;
        }
        return dp_i;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2,3,2};
        int rob = rob(nums);
        System.out.println(rob);

        nums = new int[]{1,2,3,1};
        int rob2 = rob(nums);
        System.out.println(rob2);
    }
}
