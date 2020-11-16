package leetcode;


import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 20 有效的括号
 * easy
 * https://leetcode-cn.com/problems/valid-parentheses
 */
public class Q20_ValidParentheses {

    /**
     * 1.遍历 str
     * 2.遇到左括号入栈
     * 3.遇到右括号出栈，当前字符与出栈字符不相等证明不匹配
     * 4.直到最后，栈不为空时，证明还剩一个不匹配的，返回false
     *   栈为空，返回true
     */
    public boolean isValid(String s) {
        if (s == null || s.isEmpty())
            return false;

        char[] str = s.toCharArray();
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : str) {
            if (c == '{') {
                // 压入对应的右括号
                stack.push('}');
            }
            if (c == '[') {
                stack.push(']');
            }
            if (c == '(') {
                stack.push(')');
            }

            if (c == '}' || c == ')' || c == ']') {
                if (stack.isEmpty()) { // 遇到右括号，栈为空也是错误的
                    return false;
                }
                Character pop = stack.pop();
                if (pop != c) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        Q20_ValidParentheses q = new Q20_ValidParentheses();
        System.out.println("case 1 true: " + q.isValid("()"));
        System.out.println("case 2 true: " + q.isValid("()[]{}"));
        System.out.println("case 3 false: " + q.isValid("(]"));
        System.out.println("case 4 false: " + q.isValid("([)]"));
        System.out.println("case 5 true: " + q.isValid("{[]}"));
    }
}
