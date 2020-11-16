package leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 22 括号生成
 * medium
 * https://leetcode-cn.com/problems/generate-parentheses/
 */
public class Q22_GenerateParentheses {

    /**
     * n 为多少个左括号
     */
    public List<String> generateParenthesis(int n) {
        if (n == 0)
            return Collections.emptyList();

        List<String> result = new ArrayList<>();
        char[] path = new char[n * 2]; // n 对括号，最后生成 2n 长度的字符串
        process(path, 0, 0, n, result);
        return result;
    }

    /**
     * 1.终止条件：如果 index == path.length 收集一个答案
     * 2.如果 left 还有剩余，则证明能填充 '('
     * 3.如果 needRight 大于0，则证明能填充 ')'
     *
     * @param path 其中一个解
     * @param index 计算到 path 中的哪一个元素
     * @param needRight 需要多少个')'，当遇到'('时，needRight + 1
     * @param left   还剩多少个 '(' 能填
     * @param result 答案收集
     */
    private void process(char[] path, int index, int needRight, int left, List<String> result) {
        if (path.length == index) {
            result.add(String.valueOf(path));
            return;
        }

        if (left > 0) {
            path[index] = '(';
            process(path, index + 1, needRight + 1, left - 1, result);
        }

        if (needRight > 0) {
            path[index] = ')';
            process(path, index + 1, needRight - 1, left, result);
        }
    }

    public static void main(String[] args) {
        Q22_GenerateParentheses q = new Q22_GenerateParentheses();
        List<String> strs = q.generateParenthesis(3);
        System.out.println(strs);
    }
}
