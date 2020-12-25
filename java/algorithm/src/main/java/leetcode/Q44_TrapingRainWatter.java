package leetcode;

/**
 * 接雨水
 * https://leetcode-cn.com/problems/trapping-rain-water/
 *
 * @author zhangxiaofan 2020/12/25-16:15
 */
public class Q44_TrapingRainWatter {

    /**
     * 暴力解
     * 走到 i 处，向前走，找到最高的部分 max1
     *           向后走，找到最高的部分 max2
     * 则 i 处可承接的雨水量为 min(max1, max2) - height[i]
     *
     * O(n^2)
     *
     * @param height 柱子高度
     * @return 承接的雨水量
     */
    public static int trap1(int[] height) {
        int water = 0;
        for (int i = 0; i < height.length; i++) {
            int currH = height[i];

            // 向前走，找 max1
            int max1 = currH;
            int x = i - 1;
            while (x >= 0) {
                int h = height[x];
                if (h > max1) {
                    max1 = h;
                }
                x--;
            }

            // 向后走，找 max2
            int max2 = currH;
            x = i + 1;
            while (x < height.length) {
                int h = height[x];
                if (h > max2) {
                    max2 = h;
                }
                x++;
            }

            int w = Math.min(max1, max2) - currH;
            water += w;
        }
        return water;
    }

    /**
     * 预处理，减少 max1， max2 的计算
     *
     * O(n)
     * @param height 柱子高度
     * @return 承接雨水量
     */
    public static int trap2(int[] height) {
        // max1 的预处理
        int[] max1 = new int[height.length];
        int m = 0;
        for (int i = 0; i < height.length; i++) {
            m = Math.max(m, height[i]);
            max1[i] = m;
        }

        // max2 的预处理
        int[] max2 = new int[height.length];
        m = 0;
        for (int i = height.length - 1; i >= 0; i--) {
            m = Math.max(m, height[i]);
            max2[i] = m;
        }

        int water = 0;
        for (int i = 0; i < height.length; i++) {
            int h = height[i];
            int m1 = max1[i];
            int m2 = max2[i];
            int w = Math.min(m1, m2) - h;
            water += w;
        }
        return water;
    }

    public static void main(String[] args) {
        int[] height = new int[] {0,1,0,2,1,0,1,3,2,1,2,1};
        int w = trap1(height);
        System.out.println(w);
        w = trap2(height);
        System.out.println(w);

        height = new int[] {4,2,0,3,2,5};
        w = trap1(height);
        System.out.println(w);
        w = trap2(height);
        System.out.println(w);
    }
}
