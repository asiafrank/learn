package leetcode;

/**
 * 两数相加
 * https://leetcode-cn.com/problems/add-two-numbers
 * <p>
 * 给出两个非空的链表用来表示两个非负的整数。其中，
 * 它们各自的位数是按照逆序的方式存储的，并且它们的每个节点只能存储一位数字。
 * <p>
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * <p>
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * <p>
 * 示例：
 * <p>
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 */
public class Q2_AddTwoNumbers {
    public static class ListNode {
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

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null)
            return null;

        ListNode p1 = l1;
        ListNode p2 = l2;

        ListNode pre = null;
        ListNode head = null;
        int carry = 0;
        while (p1 != null || p2 != null || carry > 0) {
            int p1V = 0;
            int p2V = 0;
            if (p1 != null) {
                p1V = p1.val;
            }
            if (p2 != null) {
                p2V = p2.val;
            }

            int v = p1V + p2V + carry;
            carry = v / 10;
            v = v % 10;

            ListNode curr = new ListNode(v);
            if (pre != null) {
                pre.next = curr;
            }
            pre = curr;
            if (head == null) {
                head = curr;
            }

            if (p1 != null) {
                p1 = p1.next;
            }
            if (p2 != null) {
                p2 = p2.next;
            }
        }

        return head;
    }

    public static void main(String[] args) {
        test1();
        test2();
    }

    private static void test1() {
        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
        ListNode l = addTwoNumbers(l1, l2);
        printListNode(l);
    }

    private static void test2() {
        ListNode l1 = new ListNode(9, new ListNode(9, new ListNode(9)));
        ListNode l2 = new ListNode(9, new ListNode(9));
        ListNode l = addTwoNumbers(l1, l2);
        printListNode(l);
    }

    private static void printListNode(ListNode l) {
        ListNode p = l;
        while (p != null) {
            System.out.print(p.val + "->");
            p = p.next;
        }
        System.out.println();
    }
}