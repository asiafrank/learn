package leetcode;

/**
 * 删除链表的倒数第N个节点
 * medium
 * https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/
 */
public class Q19_RemoveNthNodeFromEndOfList {

    private static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * 删除倒数第几个节点，返回头节点
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode pre = null;  // 要删除节点的前一个节点
        ListNode p = null;    // 要删除节点
        ListNode curr = head; // 当前迭代的节点

        int count = n;
        // 设置指针
        while (curr != null) {
            if (count == 1) { // 设置p指针
                p = head;
            } else if (count == 0) { // 设置pre指针
                assert p != null;
                pre = head;
                p = p.next;
            } else if (count < 0) { // pre 和 p 往前移
                assert pre != null;
                assert p != null;
                pre = pre.next;
                p = p.next;
            }
            curr = curr.next;
            count--;
        }

        if (pre == null && p == null) { // 没有要删除的节点
            return head;
        }

        if (pre == null && p != null) { // 恰好删的是头节点
            return p.next;
        }

        // 正常情况
        pre.next = p.next;
        return head;
    }

    public static void main(String[] args) {
        // 1-->2-->3-->4-->5
        ListNode head = new ListNode(1,new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, null)))));

        // 删除 4 节点
        Q19_RemoveNthNodeFromEndOfList q = new Q19_RemoveNthNodeFromEndOfList();
        ListNode h = q.removeNthFromEnd(head, 2);

        while (h != null) {
            System.out.print(h.val + "--->");
            h = h.next;
        }
    }
}
