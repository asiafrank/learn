package labuladong;

/**
 * 二分查找：
 * 寻找⼀个数、寻找左侧边界、寻找右侧边界。
 * 输入都是从小到大有序的数组 nums
 * @author zhangxiaofan 2020/12/17-14:33
 */
public class C3BinarySearch {
    /**
     * 二分查找寻找一个数
     * 找到元素，返回 索引；没找到，返回 -1
     *
     * 闭区间搜索：[left, right]
     */
    public static int searchNum(int[] nums, int target) {
        if (nums == null || nums.length == 0)
            return -1;

        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int n = nums[mid];
            if (n == target) {
                return mid;
            } else if (n < target) {
                left = mid + 1;
            } else if (n > target) {
                right = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 二分查找左侧边界
     * nums = [1, 2, 2, 2, 3], target = 2
     * 需要返回左侧边界 1
     */
    public static int searchNumLeftBound(int[] nums, int target) {
        if (nums == null || nums.length == 0)
            return -1;

        int left = 0, right = nums.length;
        // [left, right), 当 left == right 时，终止
        // 如果使用 [left, right] 闭区间，由于 while 内没有 return 所以无法终止导致死循环
        while (left < right) {
            int mid = (left + right) / 2;
            int n = nums[mid];
            if (n == target) {
                right = mid;
            } else if (n < target) {
                left = mid + 1;
            } else if (n > target) {
                right = mid; // 左闭右开, 所以无需 mid - 1
            }
        }

        if (left == nums.length) return -1; // 没找到
        if (nums[left] != target) return -1; // num[left] 不是目标值

        return left;
    }

    /**
     * 寻找右侧边界
     */
    public static int searchNumRightBound(int[] nums, int target) {
        if (nums == null || nums.length == 0)
            return -1;

        int left = 0, right = nums.length;
        while (left < right) {
            int mid = (left + right) / 2;
            int n = nums[mid];
            if (n == target) {
                left = mid + 1;
            } else if (n < target) {
                left = mid + 1;
            } else if (n > target) {
                right = mid;
            }
        }

        if (left == 0) return -1;
        int i = left - 1;
        if (nums[i] != target) return -1;

        return i;
    }

    public static void main(String[] args) {
        int[] nums = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int r = searchNum(nums, 7);
        System.out.println(r);

        nums = new int[] {1, 2, 2, 2, 2, 3};
        r = searchNumLeftBound(nums, 2);
        System.out.println(r);

        r = searchNumRightBound(nums, 2);
        System.out.println(r);
    }
}
