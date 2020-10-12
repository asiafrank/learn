package leetcode;

import java.util.Arrays;

/**
 * 3.无重复字符的最长子串
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 * @author zhangxiaofan 2020/10/12-10:25
 */
public class Q3_LongestSubstringWithoutRepeatingCharacters {

    /**
     * i 为数组下标，问题转换为：我们计算当以 i 作为结尾时，最长的子串长度是多少？
     *
     * 1.遍历数组，i 为当前元素。
     * 2.i 的字符在 map 中有没有出现过
     *   a.如果出现过，则这个出现过的 下标与 (i - preLen) 取最大值，这个值就是当前 i 往前推所能到达的最远的下标
     *   b.如果没有出现过，则 preLen + 1 就是当前 i 往前推所能到达的最远的下标
     * 3.统计整个遍历过程中最长的 子串长度 结果 (maxLen)
     */
    public static int lengthOfLongestSubstring(String s) {
        int[] map = new int[256]; // ascii 码长度的数组，充当 map。key = 英文字符，value = 该字符最近一次出现的下标
        // 初始化为 -1
        Arrays.fill(map, -1);

        int preLen = 0; // 前一个 i 作为结尾时，包含 i 的子串最长长度。
        int maxLen = 0; // 整个数组的最长子串的长度

        char[] arr = s.toCharArray();
        int length = arr.length;
        for (int i = 0; i < length; i++) {
            char c = arr[i];
            int lastOccurIndex = map[c];
            map[c] = i; // 更新当前字符在 map 中的 index

            if (lastOccurIndex == -1) { // 如果没有出现过
                preLen = preLen + 1;
            } else { // 如果出现过
                int x = Math.max(lastOccurIndex, i - preLen - 1); // 最多能往前推到 x 下标处, 不包含那个字符所以 - 1
                preLen = i - x; // 子串长度
            }

            if (preLen > maxLen) { // 计算最大的子串长度 maxLen
                maxLen = preLen;
            }
        }
        return maxLen;
    }

    public static void main(String[] args) {
        String s = "abcabcbb"; // 3
        String s1 = "bbbbb";  // 1
        String s2 = "pwwkew"; // 3
        String s3 = "tmmzuxt"; // 5
        int maxLen = lengthOfLongestSubstring(s3);
        System.out.println(maxLen);
    }
}
