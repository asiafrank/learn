package leetcode;

/**
 * 实现 strStr()
 * 即KMP算法匹配字符串
 * hard(easy?)
 * https://leetcode-cn.com/problems/implement-strstr/
 */
public class Q28_ImplementStrStr {

    /**
     * KMP 匹配字符串
     *
     * @param str   需要匹配的串
     * @param match 模式串
     * @return 返回匹配的第一个字符
     */
    public int strStr(String str, String match) {
        if (str == null || match == null)
            return -1;
        if (str.length() < match.length())
            return -1;
        if (str.isEmpty() || match.isEmpty())
            return 0;

        // next 数组
        char[] chars = str.toCharArray();
        char[] matchArr = match.toCharArray();
        int[] next = getNextArray(matchArr);
        int ni = 0; // matchArr 数组下标
        int i = 0;  // chars 数组下标
        while (ni != next.length && i != chars.length) {
            if (chars[i] == matchArr[ni]) { // 匹配时，一起往右移动
                i++;
                ni++;
            } else { // 不匹配时
                if (ni == 0) { // 已经跳到next头部，i右移
                    i++;
                } else {           // 跳到 next 对应位置，继续匹配
                    ni = next[ni];
                }
            }
        }

        // 如果匹配成功，返回匹配的第一个字符位置
        // 不匹配成功，则返回 -1
        boolean isMatch = ni == next.length;
        return isMatch ? i - ni : -1;
    }

    // 求 next 数组
    private int[] getNextArray(char[] matchArr) {
        int[] next = new int[matchArr.length];
        next[0] = -1;
        if (matchArr.length == 1) {
            return next;
        }
        next[1] = 0;

        int i = 2;
        // cn 代表，next 位置的值
        int cn = 0; // cn 第一次，就是指 next[1] 时的值
                    // cn 第一次，要 matchArr[0] 与 matchArr[1] 比较
        while (i < next.length) {
            if (matchArr[cn] == matchArr[i - 1]) { // 如果匹配，
                next[i] = cn + 1;
                i++;
                cn++; // 这个 next[i] 直接供下一次匹配使用
            } else {
                if (cn > 0) {
                    cn = next[cn];
                } else {
                    next[i] = 0;
                    i++;
                }
            }
        }
        return next;
    }

    public static void main(String[] args) {
        Q28_ImplementStrStr q = new Q28_ImplementStrStr();
        String str = "hello";
        String match = "llo";
        int i = q.strStr(str, match);
        System.out.println(i == 2);
    }
}
