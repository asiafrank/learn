package leetcode;

/**
 * Integer 转 罗马数字
 * https://leetcode-cn.com/problems/roman-to-integer
 */
public class Q12_IntegerToRoman {
    public static String intToRoman(int num) {
        String[][] c = {
                {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"},
                {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"},
                {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"},
                {"", "M", "MM", "MMM"}};
        StringBuilder roman = new StringBuilder();
        roman.append(c[3][num / 1000 % 10])
             .append(c[2][num / 100 % 10])
             .append(c[1][num / 10 % 10])
             .append(c[0][num % 10]);
        return roman.toString();
    }
}
