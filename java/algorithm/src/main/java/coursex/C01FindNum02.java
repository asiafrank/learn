package coursex;

/**
 * 2. 怎么把一个int类型的数，提取出最右侧的1来?
 * 如：5 二进制形式是 0101 提取结果是 0001
 * @author zhangxiaofan 2020/08/19-10:18
 */
public class C01FindNum02 {
    public static void main(String[] args) {
        // 先取反再加1，相当于把最右侧的那个 1 暴露出来
        // 然后按位与，结果就是只有最右侧那个 1，其他都变为0
        int i = 5;
        int result = i & (~i + 1);
        assert result == 1;
        System.out.println(result);

        System.out.println(i + ":" + Integer.toBinaryString(i));
        System.out.println(result + ":" + Integer.toBinaryString(result));
    }
}
