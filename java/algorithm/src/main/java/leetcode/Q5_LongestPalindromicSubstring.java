package leetcode;

import java.util.Arrays;

/**
 * 最长回文子串
 * medium
 * https://leetcode-cn.com/problems/longest-palindromic-substring/
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 *
 * 示例 1：
 * 输入: "babad"
 * 输出: "bab"
 * 注意: "aba" 也是一个有效答案。
 *
 * 示例 2：
 * 输入: "cbbd"
 * 输出: "bb"
 */
public class Q5_LongestPalindromicSubstring {

    public static String longestPalindrome(String s) {
        if (s == null)
            return null;

        char[] max = new char[]{};
        char[] str = s.toCharArray();
        for (int i = 0; i < str.length; i++) {
            char[] s1 = palindromic(str, i, i); // 找奇数的回文串
            char[] s2 = palindromic(str, i, i + 1); // 找偶数的回文串

            if (max.length < s1.length) {
                max = s1;
            }
            if (max.length < s2.length) {
                max = s2;
            }
        }
        return String.copyValueOf(max);
    }

    /**
     * 根据 l,r 向两边走，判断回文串
     * @param s 字符串数组
     * @param l 左边开始下标
     * @param r 右边开始下标
     * @return 回文串
     */
    private static char[] palindromic(char[] s, int l, int r) {
        while (l >= 0 && r < s.length) {
            if (s[l] != s[r])
                break;
            l--;
            r++;
        }
        // for 循环结束时，l 会多减了 1
        // 所以 [l + 1, r) 才是回文串
        return Arrays.copyOfRange(s, l + 1, r);
    }

    // TODO: manacher 方法

    public static void main(String[] args) {
        String s = "babad";
        String p = longestPalindrome(s);
        System.out.println(p);
    }
}
