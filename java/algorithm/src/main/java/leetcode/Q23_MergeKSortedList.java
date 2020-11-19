package leetcode;

import java.util.PriorityQueue;

/**
 * 合并k个有序链表
 * hard
 * https://leetcode-cn.com/problems/merge-k-sorted-lists/
 */
public class Q23_MergeKSortedList {
    public class ListNode {
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

    // 小根堆排序，输出到一个链表中
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> queue = new PriorityQueue<>();
        // TODO:
        return null;
    }
}
