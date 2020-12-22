package labuladong.dp;

import java.util.Arrays;

/**
 * 最长回文子串
 * 遍历字符串，将 arr[i]/arr[i]与arr[i+1] 当做中心向左右两边匹配，
 * 如果匹配成功，则就是回文串返回。
 *
 * @author zhangxiaofan 2020/12/22-09:34
 */
public class C04LongestPalindrome {

    //------ 暴力做法 ---------

    public static String longestPalindrome(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }

        char[] str = s.toCharArray();
        char[] rs = new char[]{};
        for (int i = 0; i < str.length; i++) {
            char[] s1 = palindrome(str, i, i); // 以 i 为中心，向两边扩。
            char[] s2 = palindrome(str, i, i+1); // 以 i 和 i+1 为中心，向两边扩。

            if (s1.length > rs.length) {
                rs = s1;
            }
            if (s2.length > rs.length) {
                rs = s2;
            }
        }
        return String.copyValueOf(rs);
    }

    /**
     * 从 i，j 向两边扩，找回文串
     */
    private static char[] palindrome(char[] str, int i, int j) {
        while (i >= 0 && j < str.length) {
            if (str[i] == str[j]) {
                i--;
                j++;
            } else {
                break;
            }
        }
        return Arrays.copyOfRange(str, i + 1, j);
    }

    // 动态规划做法比暴力做法复杂所以不写了

    // TODO: manacher 实现

    public static void main(String[] args) {
        String s = longestPalindrome("ababaiiuuiio");
        System.out.println(s);
    }
}
