package coursex;

/**
 * 3. 一个数组中有两种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这两种数?
 * 如：[1,1,1,2,2,3] 出现奇数次的两个数为 1 和 3
 *
 * @author zhangxiaofan 2020/08/19-10:43
 */
public class C01FindNum03 {
    public static void main(String[] args) {
        /*
        假设这两个数是 a，b
        • 先全部数异或，所得结果为 eor = a ^ b.
        • 提取最右侧的 1 的值 onlyOne
        • 再遍历一次数组，当 onlyOne & 元素所得不为 0 时，做异或，
          等待遍历结束，那么 onlyOne 的值就是 a 或 b 其中一个，假设是 a
        • 那么 b = eor ^ onlyOne
        • 到此，a 与 b 都得到了
         */

        int[] arr = new int[]{1, 1, 1, 2, 2, 3};
        int eor = 0;
        for (int i : arr) {
            eor ^= i;
        }
        // 设 a b 就是两个出现奇数次的数，这里 eor 相当于 a ^ b
        // 提取 eor 中最右侧的 1，用来区分 a 和 b。
        int onlyOne = eor & (~eor + 1);
        int a = 0;
        // 找出那个 onlyOne 匹配的数赋值给 a
        for (int i : arr) {
            if ((i & onlyOne) == onlyOne) { // 只有满足 onlyOne 的进入
                a ^= i;
            }
        }
        int b = eor ^ a; // a 已经得到了。那么 b 异或一下就可已得到
        System.out.println(a);
        System.out.println(b);
    }
}
