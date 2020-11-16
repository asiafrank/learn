package leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 电话号码的字母组合
 * medium
 * https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/
 * 很简单，就是暴力递归
 */
public class Q17_LetterCombinationsOfAPhoneNumber {

    public static char[][] phone = {
            { 'a', 'b', 'c' }, // 2    0
            { 'd', 'e', 'f' }, // 3    1
            { 'g', 'h', 'i' }, // 4    2
            { 'j', 'k', 'l' }, // 5    3
            { 'm', 'n', 'o' }, // 6
            { 'p', 'q', 'r', 's' }, // 7
            { 't', 'u', 'v' },   // 8
            { 'w', 'x', 'y', 'z' }, // 9
    };

    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.isEmpty())
            return Collections.emptyList();

        char[] str = digits.toCharArray();
        char[] path = new char[str.length];

        List<String> collect = new ArrayList<>();
        process(collect, str, 0, path);
        return collect;
    }

    private int getPhoneIndex(char pushNum) {
        return pushNum - '2';
    }

    // 暴力递归
    private void process(List<String> collect, char[] str, int index, char[] path) {
        if (index == str.length) {
            collect.add(String.valueOf(path));
            return;
        }

        char pushNum = str[index];
        char[] chars = phone[getPhoneIndex(pushNum)];
        for (char c : chars) {
            path[index] = c;
            process(collect, str, index + 1, path);
        }
    }

    public static void main(String[] args) {
        Q17_LetterCombinationsOfAPhoneNumber s = new Q17_LetterCombinationsOfAPhoneNumber();
        List<String> strs = s.letterCombinations("23");
        strs.forEach(System.out::println);
    }
}
