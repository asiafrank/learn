package coursex.ad;

/**
 * KMP算法
 *
 * next数组含义：next[i] = m，代表 match字符串中 i 字符的前一个字符的相同前后缀长度为 m。
 */
public class C3KMP {

    public static int indexOf(String s, String m) {
        if (s == null || m == null || s.isEmpty() || m.isEmpty())
            return -1;

        char[] str = s.toCharArray();
        char[] match = m.toCharArray();
        int[] next = getNext(match);

        int mi = 0; // match index
        int i = 0;
        while (i < str.length && mi < match.length) {
            if (str[i] == match[mi]) {
                mi = mi + 1;
                i++;
            } else {
                if (mi == 0) {
                    i++;
                } else {
                    mi = next[mi];
                }
            }
        }

        if (mi == match.length) return i - mi;
        return -1;
    }

    /**
     * 给 match 数组维护 next 数组
     */
    private static int[] getNext(char[] match) {
        int[] next = new int[match.length];
        next[0] = 0; // match[0]字符，无前一个字符，初始化为 0
        if (match.length >= 2)
            next[1] = 0; // match[1]字符

        int mi = 2; // match 的下标
        int ni = 1; // next 的下标
        for (; mi < match.length;) {
            if (match[next[ni]] == match[mi]) {
                next[mi] = next[ni] + 1;
                mi++;
                ni = mi - 1;
            } else {
                if (ni <= 0) { // next 条到头了，直接匹配下一个字符
                    next[mi] = 0;
                    mi++;
                    ni = mi - 1;
                } else {
                    ni = next[ni];
                }
            }
        }
        return next;
    }

    public static void main(String[] args) {
//        test1();
        test2();
    }

    private static void test2() {
        String s = "hello";
        String m = "llo";
        int i = indexOf(s, m);
        System.out.println(i); // 2
    }

    private static void test1() {
        String s = "abcdefg";
        String m = "def";
        int i = indexOf(s, m);
        System.out.println(i); // 3
    }
}
