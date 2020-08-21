package coursex;

/**
 * 4.输出二进制1的个数 (Hamming Weight, 汉明权重)
 * 如：5 二进制是 0101 则个数是 2
 * @author zhangxiaofan 2020/08/19-10:58
 */
public class C01CountBit {
    public static void main(String[] args) {
        countBit1();
        countBit2();
        countBit3();
    }

    /*
    每次右移一位，然后计算这一位是不是1。
    这种计算弊端是位数为 0 的也做了比较，常数项复杂度偏高
     */
    private static void countBit1() {
        int a = 5;
        int count = 0;
        while (a != 0) {
            if ((a & 1) == 1) {
                count++;
            }
            a = a >>> 1;
        }
        System.out.println(count);
    }

    /*
    通过 取反加1再与运算，得最右边的1，count++
    直到变为0
     */
    private static void countBit2() {
        int a = -5;
        System.out.println(Integer.toBinaryString(a));
        int count = 0;
        while (a != 0) {
            int onlyOne = a & (~a + 1);
            count++;
            a ^= onlyOne; // 去除 onlyOne
        }
        System.out.println(count);
    }

    /*
     n & (n - 1) 能直接消除最右边的 1
     所以，不断消除 1，直到 变成 0 为止
     */
    private static void countBit3() {
        int a = -5;
        int count = 0;
        while (a != 0) {
            a = a & (a - 1);
            count++;
        }
        System.out.println(count);
    }
}
