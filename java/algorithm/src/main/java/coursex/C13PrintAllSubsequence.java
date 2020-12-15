package coursex;

import com.asiafrank.util.Printer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 打印一个字符串中的所有子序列
 *
 * 子串：连续的
 * 子序列：可以不连续
 */
public class C13PrintAllSubsequence {

    //-------------收集 src 的子序列--------------------

    /**
     * 收集 src 的子序列
     */
    public static List<String> allSubsequence1(String src) {
        if (src == null || src.isEmpty())
            return null;

        char[] arr = src.toCharArray();
        List<String> ans = new ArrayList<>();

        process1(arr, 0, ans, "");
        return ans;
    }

    /**
     * 递归收集子序列答案
     * @param arr   源数组
     * @param index 当前做决策的 arr 元素下标
     * @param ans   收集子序列答案
     * @param path  收集过程的路径，代表一种选择分支
     */
    private static void process1(char[] arr, int index, List<String> ans, String path) {
        if (index >= arr.length) {
            ans.add(path);
            return;
        }

        String no = path; // 没有收集当前元素
        process1(arr, index + 1, ans, no);

        String yes = path + arr[index]; // 选择了当前元素
        process1(arr, index + 1, ans, yes);
    }

    //-------------收集 src 的子序列, 不能重复--------------------

    /**
     * 收集 src 的子序列
     */
    public static Set<String> allSubsequence2(String src) {
        if (src == null || src.isEmpty())
            return null;

        char[] arr = src.toCharArray();
        Set<String> ans = new HashSet<>();

        process2(arr, 0, ans, "");
        return ans;
    }

    /**
     * 递归收集子序列答案
     * @param arr   源数组
     * @param index 当前做决策的 arr 元素下标
     * @param ans   收集子序列答案
     * @param path  收集过程的路径，代表一种选择分支
     */
    private static void process2(char[] arr, int index, Set<String> ans, String path) {
        if (index >= arr.length) {
            ans.add(path);
            return;
        }

        String no = path; // 没有收集当前元素
        process2(arr, index + 1, ans, no);

        String yes = path + arr[index]; // 选择了当前元素
        process2(arr, index + 1, ans, yes);
    }

    public static void main(String[] args) {
        List<String> ans1 = allSubsequence1("abc");
        Printer.printColl(ans1);
        Set<String> ans2 = allSubsequence2("aaa");
        Printer.printColl(ans2);
    }
}
