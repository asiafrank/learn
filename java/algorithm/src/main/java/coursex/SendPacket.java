package coursex;

import java.util.Arrays;

/**
 * 快递运输
 *
 * 一辆运送快递的货车，运送的快递均放在大小不等的长方体快递盒中，
 * 为了能够装载更多的快递，同时不能让货车超载，需要计算最多能装多少个快递。
 * 注：快递的体积不受限制，快递数最多1000 个，货车载重量最大 50000
 *
 * 输入：第一行输入每个快递的重量，用英文逗号分隔，如: 5,10,2,11
 * 第二行输入货车的载重量，如：20
 * 答：3个包裹
 */
public class SendPacket {

    /**
     * 计算最多能装多少个快递。
     * 递归方法
     * @param packets  快递重量
     * @param capacity 货车载重量
     * @return 最多能装多少个
     */
    public static int send(int[] packets, int capacity) {
        return process(packets, 0, capacity);
    }

    /**
     * 计算最多能装多少个快递。
     * @param packets  快递重量
     * @param i        对 packets[i] 做选择
     * @param capacity 货车载重量
     * @return 最多能装多少个
     */
    public static int process(int[] packets, int i, int capacity) {
        if (i == packets.length) {
            return 0;
        }

        int delta = capacity - packets[i];
        if (delta < 0) { // 无法选择
            return process(packets, i + 1, capacity);
        }

        // 选与不选取最大
        int choose = 1 + process(packets, i + 1, delta);
        int noChoose = process(packets, i + 1, capacity);
        return Math.max(choose, noChoose);
    }

    /**
     * 贪心
     * 只需给 packets 排序，从小到大往货车装。
     * 直到装不进为止。
     *
     * @return 最多能装多少个
     */
    public static int greedySend(int[] packets, int capacity) {
        Arrays.sort(packets);

        int count = 0;
        for (int p : packets) {
            if (p < capacity) { // 能装入
                count++;
                capacity = capacity - p;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int[] packets = new int[] {5,10,2,11};
        int send = send(packets, 20);
        System.out.println(send);

        send = greedySend(packets, 20);
        System.out.println(send);
    }

    // TODO: dp 动态规划做法 capacity 从 0 -> capacity
    //       dp[i] = x 代表 i 容量的货车能装 x 个货物

}
