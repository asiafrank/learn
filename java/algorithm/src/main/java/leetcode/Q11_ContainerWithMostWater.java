package leetcode;

/**
 * 盛最多水的容器
 * medium
 * https://leetcode-cn.com/problems/container-with-most-water
 */
public class Q11_ContainerWithMostWater {
    public static int maxArea(int[] height) {
        int max = 0;
        int l = 0;
        int r = height.length - 1;
        while (l != r) {
            int lHeight = height[l];
            int rHeight = height[r];
            int area;
            if (lHeight > rHeight) {
                area = rHeight * (r - l);
                r--;
            } else {
                area = lHeight * (r - l);
                l++;
            }

            if (area > max) {
                max = area;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[] arr1 = new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7};
        int rs1 = maxArea(arr1);
        System.out.println(rs1 == 49);
    }
}
