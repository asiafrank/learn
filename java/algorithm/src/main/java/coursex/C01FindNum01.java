package coursex;

/**
 * 1. 一个数组中有一种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这种数 ？
 * 如：[2,2,1,1,3,3,4] 打印 4
 * @author zhangxiaofan 2020/08/19-09:53
 */
public class C01FindNum01 {
    public static void main(String[] args) {
        // 做异或操作，x ^ x = 0, 异或满足交换律和结合律，数字交换位置异或对结果不影响。
        // 所有的偶数异或等于 0。再异或一个奇数次的数，得到的就是结果
        int[] arr = new int[]{2, 2, 1, 1, 3, 3, 4};
        int result = 0;
        for (int a : arr) {
            result ^= a;
        }
        assert result == 4;
        System.out.println(result);
    }
}
