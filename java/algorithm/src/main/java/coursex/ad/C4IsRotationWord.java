package coursex.ad;

/**
 * 是否旋转词
 *
 * ‘123456’ 与 '234561' 互为旋转词
 *
 * 过程：
 * 1. ‘123456’ 复制拼接为 '123456123456'
 * 2. 用 kmp 匹配，如果存在，则必定是互为旋转词
 */
public class C4IsRotationWord {

    public static boolean isRotationWord(String s1, String s2) {
        if (s1 == null || s2 == null)
            return false;

        if (s1.isEmpty() || s2.isEmpty())
            return false;

        // 1. 复制拼接为 2倍
        String s = s1 + s1;

        // 2.kmp 匹配，如果大于 1，则表示匹配成功; 为互为旋转词
        return indexOf(s, s2) > 0;
    }

    private static int indexOf(String s, String m) {
        if (s == null || m == null)
            return -1;

        char[] str = s.toCharArray();
        char[] match = m.toCharArray();
        int[] next = getNext(match);

        int i = 0;  // str index
        int mi = 0; // match index
        while (i < str.length && mi < match.length) {
            if (str[i] == match[mi]) {
                mi++;
                i++;
            } else {
                if (mi == 0) { // next 跳到顶了，匹配下一个 str[i]
                    i++;
                } else { // next 往前眺
                    mi = next[mi];
                }
            }
        }

        if (mi == match.length) { // match 都匹配上了
            return i - mi;
        }
        return -1;
    }

    private static int[] getNext(char[] match) {
        int[] next = new int[match.length];
        // next[0], next[1] 都初始化为 0

        int mi = 2; // match index
        int ni = 1; // next index
        while (mi < match.length) {
            if (match[next[ni]] == match[mi]) {
                next[mi] = next[ni] + 1;
                mi++;
                ni = mi - 1;
            } else {
                if (ni == 0) { // 已经到 next 顶了，跳过匹配下一个 mi
                    mi++;
                    ni = mi - 1;
                } else {
                    ni = next[ni]; // 调到之前的 next
                }
            }
        }

        return next;
    }

    public static void main(String[] args) {
        String s1 = "12345";
        String s2 = "34512";
        boolean rotationWord = isRotationWord(s1, s2);
        System.out.println(rotationWord);
    }

}
