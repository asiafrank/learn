package crls.divide;

/**
 * 第4章-分治策略-习题 4.1-5 最大连续子数组
 *
 * @author zhangxf created at 2/11/2019.
 */
public class MaximumSubarray {

    /**
     * @param a 原数组
     * @return 返回最大连续子数组
     */
    public static int[] solution(int[] a) {
        int currentSum = 0;
        int maxSum = -1;
        int maxStart = -1;
        int maxEnd = -1;
        for (int i = 0; i < a.length; i++) {
            currentSum = currentSum + a[i];
            if (currentSum <= 0) {
                currentSum = 0;
                maxSum = 0;
                maxStart = i + 1;
                maxEnd = 0;
            } else if (currentSum > maxSum) {
                maxSum = currentSum;
                maxEnd = i;
            }
        }

        int[] r = new int[maxEnd - maxStart + 1];
        System.arraycopy(a, maxStart, r, 0, r.length);
        return r;
    }
}
