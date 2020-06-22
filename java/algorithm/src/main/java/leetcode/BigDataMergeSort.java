package leetcode;

/**
 * 大数据归并排序。
 * 假设只有 50M 的内存空间，读取并排序一个 100G 的文件，该文件内每一行都是 int 值。
 *
 * 思路：
 * 1. 50M一次读取，做排序，放到一个小文件中。
 * 2. 100G的文件能分成 2000个左右的文件。
 *    进过第1步的处理，每个小文件内容都是有序的。
 * 3. 建立 2000 大小的 Integer 数组，每个文件取一个值放入这个数组中，
 *    做堆排序，pop 最小值，然后输出最小值到 result 文件中。
 *    接着再从 2000 个文件中取一个数放入堆中。
 * 4. 重复放入堆，弹出最小值，写出到 result 文件，这个步骤。
 *    直到结束
 *
 * @author zhangxiaofan 2020/06/22-09:34
 */
public class BigDataMergeSort {
    public static void main(String[] args) {

    }
}
