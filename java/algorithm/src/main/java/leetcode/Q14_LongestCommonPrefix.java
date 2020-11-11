package leetcode;

/**
 * 最长公共前缀
 * easy
 * https://leetcode-cn.com/problems/longest-common-prefix/
 */
public class Q14_LongestCommonPrefix {

    /**
     * 以第一个字符串为基准，分别和各个字符串做匹配。
     * 记录最小的下标，遍历完后这个最小下标值就是最长公共前缀。
     */
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        if (strs.length == 1) {
            return strs[0];
        }

        char[] base = strs[0].toCharArray();
        int baseLen = base.length;
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < strs.length; i++) {
            char[] currStr = strs[i].toCharArray();
            int len = Math.min(baseLen, currStr.length);

            int index = 0;
            for (int j = 0; j < len; j++) {
                if (base[j] != currStr[j]) {
                    break;
                }
                index++;
            }
            min = Math.min(index, min);
            if (min == 0) {
                return "";
            }
        }
        return strs[0].substring(0, min);
    }

    public static void main(String[] args) {
        final String s0 = "abc___";
        final String s1 = "abcdefg";
        final String s2 = "abafafaf";
        String s = longestCommonPrefix(new String[]{s0, s1, s2});
        System.out.println(s);
    }
}
