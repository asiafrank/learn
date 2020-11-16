package leetcode;

/**
 * 正则表达式匹配
 * https://leetcode-cn.com/problems/regular-expression-matching/
 */
public class Q10_RegularExpressionMatching {

    public boolean isMatch(String s, String p) {
        if (s == null || p == null) {
            return false;
        }

        if (s.isEmpty() && p.isEmpty()) {
            return true;
        }

        char[] str = s.toCharArray();
        char[] pt = p.toCharArray();
        return isValid(str, pt) && process(str, 0, pt, 0);
    }

    /**
     * 递归判断
     * 限制 pi 传进来该位置不能为 '*'
     */
    private boolean process(char[] s, int si, char[] pt, int pi) {
        // 终止条件
        if (si == s.length) {
            if (pi == pt.length) {
                return true;
            }

            // 当前 pi 为 a*b*c* 的情况，可以判断为 0个字符
            if (pi + 1 < pt.length) {
                if (pt[pi + 1] == '*') {
                    return process(s, si, pt, pi + 2);
                }
            }
            return false;
        }

        // si 没越界, pi 越界了
        if (pi >= pt.length) {
            return false;
        }

        // si pi 都没越界
        if (pi + 1 >= pt.length || pt[pi + 1] != '*') { // [pi+1] 不是 *
            // 情况1： [si] == [pi]
            // 情况2：[pi] == '.'
            if (s[si] == pt[pi] || pt[pi] == '.') {
                return process(s, si + 1, pt, pi + 1);
            } else {
                return false;
            }
        } else { // [pi+1] 是 *
            // 情况1：[si] == [pi]
            // 情况2：[pi] == '.'
            if (s[si] == pt[pi] || pt[pi] == '.') {
                // aaaab
                // a*aaaab  "a*"匹配 0个a 的情况
                if (process(s, si, pt, pi + 2)) {
                    return true;
                }

                // 特殊情况：a* 匹配多个 a 的情况
                // s  = aaaabcd
                // pt = a*abcd
                // 则需要循环 si++, 每一个 [si] 判断 * 跳过的情况, 只要出现成功的则 返回 true
                while (si < s.length && (s[si] == pt[pi] || pt[pi] == '.')) {
                    if (process(s, si + 1, pt, pi + 2)) {
                        return true;
                    }
                    si++;
                }
            }

            else { //  情况3：[pi+1] 是 *, 且 [si] != [pi] 时
                return process(s, si, pt, pi + 2); // 跳过 *
            }
        }
        return false;
    }

    //---------------------------------------------------


    // 记忆化搜索，动态规划做法
    public boolean isMatchDP(String s, String p) {
        if (s == null || p == null) {
            return false;
        }

        if (s.isEmpty() && p.isEmpty()) {
            return true;
        }

        char[] str = s.toCharArray();
        char[] pt = p.toCharArray();
        int[][] dp = new int[str.length + 1][pt.length + 1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                dp[i][j] = -1;
            }
        }
        return isValid(str, pt) && processDP(str, 0, pt, 0, dp);
    }

    /**
     * 递归判断
     * 限制 pi 传进来该位置不能为 '*'
     * 执行超时？？
     */
    private boolean processDP(char[] s, int si, char[] pt, int pi, int[][] dp) {
        if (dp[si][pi] > -1) { // 计算过
            return dp[si][pi] > 0;
        }

        // 终止条件
        if (si == s.length) {
            if (pi == pt.length) {
                dp[si][pi] = 1;
                return true;
            }

            // 当前 pi 为 a*b*c* 的情况，可以判断为 0个字符
            if (pi + 1 < pt.length) {
                if (pt[pi + 1] == '*') {
                    if (processDP(s, si, pt, pi + 2, dp)) {
                        dp[si][pi] = 1;
                        return true;
                    } else {
                        dp[si][pi] = 0;
                        return false;
                    }
                }
            }
            dp[si][pi] = 0;
            return false;
        }

        // si 没越界, pi 越界了
        if (pi >= pt.length) {
            dp[si][pi] = 0;
            return false;
        }

        // si pi 都没越界
        if (pi + 1 >= pt.length || pt[pi + 1] != '*') { // [pi+1] 不是 *
            // 情况1： [si] == [pi]
            // 情况2：[pi] == '.'
            if (s[si] == pt[pi] || pt[pi] == '.') {
                if (processDP(s, si + 1, pt, pi + 1, dp)) {
                    dp[si][pi] = 1;
                    return true;
                } else {
                    dp[si][pi] = 0;
                    return false;
                }
            } else {
                dp[si][pi] = 0;
                return false;
            }
        } else { // [pi+1] 是 *
            // 情况1：[si] == [pi]
            // 情况2：[pi] == '.'
            if (s[si] == pt[pi] || pt[pi] == '.') {
                // aaaab
                // a*aaaab  匹配0个a 的情况
                if (processDP(s, si, pt, pi + 2, dp)) {
                    return true;
                }

                // 特殊情况：
                // s  = aaaabcd
                // pt = a*abcd
                // 则需要循环 si++, 每一个 [si] 判断 * 跳过的情况, 只要出现成功的则 返回 true
                while (si < s.length && (s[si] == pt[pi] || pt[pi] == '.')) {
                    if (processDP(s, si + 1, pt, pi + 2, dp)) {
                        dp[si][pi] = 1;
                        return true;
                    }
                    si++;
                }
            }

            else { //  情况3：[pi+1] 是 *, 且 [pi] 都不匹配时
                if (processDP(s, si, pt, pi + 2, dp)) { // 跳过 *
                    dp[si][pi] = 1;
                    return true;
                } else {
                    dp[si][pi] = 0;
                    return false;
                }
            }
        }

        dp[si][pi] = 0;
        return false;
    }

    /**
     * 验证 str 不包含 *，且 pt 不包含 **
     */
    private static boolean isValid(char[] str, char[] pt) {
        for (char c : str) {
            if (c == '*')
                return false;
        }
        for (int i = 0; i < pt.length - 1; i++) {
            if (pt[i] == '*' && pt[i+1] == '*')
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Q10_RegularExpressionMatching m = new Q10_RegularExpressionMatching();
        System.out.println("case1 false: " + m.isMatchDP("aa", "a"));
        System.out.println("case2 true: " + m.isMatchDP("aa", "a*"));
        System.out.println("case3 true: " + m.isMatchDP("aa", ".*"));
        System.out.println("case4 true: " + m.isMatchDP("ab", ".*"));
        System.out.println("case5 true: " + m.isMatchDP("aab", "c*a*b"));
        System.out.println("case5 false: " + m.isMatchDP("mississippi", "mis*is*p*."));
        System.out.println("case6 true: " + m.isMatchDP("mississippi", "mis*is*ip*."));
        System.out.println("case7 true: " + m.isMatchDP("bbbba", ".*a*a"));
    }
}
