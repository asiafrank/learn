package leetcode;

/**
 * 删除排序数组中的重复项
 * easy
 * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array/
 */
public class Q26_RemoveDuplicates {
    /**
     * 维护两个指针，done，cur
     * done，指向已经确定的位置
     * cur，当前元素位置
     *
     * 如果[cur] > [done]
     * 则 [cur] 移到 [done+1]，且 done++
     * 否则 cur 前进一步
     *
     * @param nums 有序的数组
     * @return 新长度
     */
    public int removeDuplicates(int[] nums) {
        int done = 0;
        for (int cur = 1; cur < nums.length; cur++) {
            int curNum = nums[cur];
            int doneNum = nums[done];
            if (curNum > doneNum) {
                nums[done + 1] = curNum;
                done++;
            }
        }
        return done + 1;
    }

    public static void main(String[] args) {
        Q26_RemoveDuplicates q = new Q26_RemoveDuplicates();

        int[] arr = new int[]{0,0,1,1,1,2,2,3,3,4};
        int l = q.removeDuplicates(arr);
        System.out.println(l == 5);
    }
}
