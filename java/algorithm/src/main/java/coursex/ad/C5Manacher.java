package coursex.ad;

/**
 * Manacher 寻找回文字串
 *
 * 1. str 转换成 # 填充的形式，用来避免奇偶情况的处理。
 *    如："12321" -> "#1#2#3#2#1#"
 * 2. pArr[i] = x 的含义是位置 i 的字符为中心回文半径为 x。
 *    C 与 R 变量的含义是以 C 为中心，最长扩到 R 位置处（不包含违规字符）
 * 3. 分情况讨论：
 *    i 在 R 外，暴力左右查看回文串最多能扩到哪里，并且更新 C 和 R
 *    i 在 R 内，查看 i 对称点 i' 的情况
 *        a. i' 回文区域在 (L..R) 内，则 pArr[i] = pArr[i']
 *        b. i' 回文区域在 [L..R] 外，则 pArr[i] = R - i
 *        c. i' 回文区域在 [L..R] 上，则 从 R 开始，尝试往外扩
 * TODO: 尝试写写
 */
public class C5Manacher {
    /**
     * 返回 s 中最大回文字串长度
     */
    public static int manacher(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = manacherString(s);
        // 回文半径的大小
        int[] pArr = new int[str.length];
        int C = -1;
        int R = -1;// 讲述中：R代表最右的扩成功的位置。中：最右的扩成功位置的，再下一个位置
        int max = Integer.MIN_VALUE;
        for (int i = 0; i != str.length; i++) {
            // i位置扩出来的答案，i位置扩的区域，至少是多大。
            pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
            while (i + pArr[i] < str.length && i - pArr[i] > -1) {
                if (str[i + pArr[i]] == str[i - pArr[i]])
                    pArr[i]++;
                else {
                    break;
                }
            }
            if (i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }
            max = Math.max(max, pArr[i]);
        }
        return max - 1;
    }

    public static char[] manacherString(String str) {
        char[] charArr = str.toCharArray();
        char[] res = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i != res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : charArr[index++];
        }
        return res;
    }

    // for test
    public static int right(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = manacherString(s);
        int max = 0;
        for (int i = 0; i < str.length; i++) {
            int L = i - 1;
            int R = i + 1;
            while (L >= 0 && R < str.length && str[L] == str[R]) {
                L--;
                R++;
            }
            max = Math.max(max, R - L - 1);
        }
        return max / 2;
    }

    // for test
    public static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        int possibilities = 5;
        int strSize = 20;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            if (manacher(str) != right(str)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
