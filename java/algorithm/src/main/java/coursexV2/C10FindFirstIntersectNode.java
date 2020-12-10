package coursexV2;


import coursexV2.util.Node;

/**
 * 给定两个可能有环也可能无环的单链表，头节点head1和head2。
 * 请实现一个函数，如果两个链表相交，请返回相交的第一个节点。
 * 如果不相交，返回null。
 * 【要求】
 * 如果两个链表长度之和为N，时间复杂度请达到O(N)，额外的空间复杂度O(1)
 *
 * 情况：
 * 1. 两链表 无环，用 noLoop
 * 2. 两链表 其中一个有环，必定不相交
 * 3. 两链表 两个都有环。bothLoop 三种情况
 *    1）各自有各自的环，不相交（环节点不相等）
 *       loop1 和 loop2，一个不动，另一个绕圈，不相遇。
 *    2）环节点本身是交点，或环节点之前有一个交点（环节点相等）
 *       环交点作为 end，找第一个交点方式和 noLoop 一样
 *    3）环一样，但是两个链表入环点不一样（环节点不相等）
 *       loop1 和 loop2 一个不动，另一个绕一圈，loop1 和 loop2 会相遇
 *
 * leetcode 有原题
 */
public class C10FindFirstIntersectNode {

    /**
     * 快慢指针
     * 1.快慢指针相遇
     * 2.相遇后，两指针都只走一步，再次相遇的点，就是环的第一个节点
     *
     * 有环，获取环的第一个节点。
     * 无环，返回null
     */
    public static Node getLoopNode(Node head) {
        if (head == null)
            return null;

        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) { // 第一次相遇，两者都只走一步
                slow = head; // slow 跳回头节点重新走
                while (true) {
                    slow = slow.next;
                    fast = fast.next;
                    if (slow == fast) // 再次相遇就是环的第一个节点
                        return slow;
                }
            }
        }
        return null; // 单链表，只要跳出循环，则视为无环
    }

    /**
     * 链表1和链表2必然无环时，判断是否有交点。
     * 如果有交点，返回第一个交点。
     * 如果没有交点，返回 null。
     *
     * 1.先找到两个链表最后一个节点，记录两链表的长度
     *   如果最后一个节点（非null）相等，则必然存在交点。
     * 2.假设第一个链表长为 M，第二个链表为 N。M>N, 且 M - N = k
     *   则第一个链表先走 k 步，然后一起走。
     *   这时如果相遇，则相遇的节点必定是第一个节点
     */
    public static Node noLoop(Node head1, Node head2) {
        if (head1 == null || head2 == null)
            return null;

        // 1.
        Node n1 = head1;
        int M = 1;
        while (n1.next != null) {
            n1 = n1.next;
            M++;
        }

        Node n2 = head2;
        int N = 1;
        while (n2.next != null) {
            n2 = n2.next;
            N++;
        }

        if (n1 != n2) { // 两个 end 不相等，无交点
            return null;
        }

        // 2.
        Node longHead,shortHead;
        int k, shortLength;
        if (M > N) {
            longHead = head1;
            shortHead = head2;
            shortLength = N;
            k = M - N;
        } else {
            longHead = head2;
            shortHead = head1;
            shortLength = M;
            k = N - M;
        }

        // 长的先走 k 步
        Node p1 = longHead;
        Node p2 = shortHead;

        while (k > 0) {
            assert p1 != null;
            p1 = p1.next;
            k--;
        }

        // 一起走, 直到相等
        while (shortLength > 0) {
            if (p1 == p2)
                return p1;
            assert p1 != null;
            assert p2 != null;
            p1 = p1.next;
            p2 = p2.next;
            shortLength--;
        }
        return p1;
    }
}
