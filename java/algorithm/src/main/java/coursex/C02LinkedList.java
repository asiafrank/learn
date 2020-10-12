package coursex;

/**
 * 链表操作练习
 * @author zhangxiaofan 2020/08/25-10:05
 */
public class C02LinkedList {

    public static void main(String[] args) {
        singleNodeExample();
        doubleNodeExample();
    }

    // 单链表实验
    private static void singleNodeExample() {
        // 实验链表
        SingleNode n0 = new SingleNode(0);
        SingleNode n1 = new SingleNode(1);
        SingleNode n2 = new SingleNode(2);
        SingleNode n3 = new SingleNode(3);
        n0.next = n1;
        n1.next = n2;
        n2.next = n3;

        SingleNode h = reverseLinkedList(n0);
        printLinkedList(h);
    }

    private static void doubleNodeExample() {
        // 实验链表
        DoubleNode n0 = new DoubleNode(0);
        DoubleNode n1 = new DoubleNode(1);
        DoubleNode n2 = new DoubleNode(2);
        DoubleNode n3 = new DoubleNode(3);
        n0.next = n1;
        n0.prev = null;
        n1.next = n2;
        n1.prev = n0;
        n2.next = n3;
        n2.prev = n1;
        n3.next = null;
        n3.prev = n2;

        DoubleNode h = reverseLinkedList(n0);
        printLinkedList(h);
    }

    // 单链表反转
    private static SingleNode reverseLinkedList(SingleNode head) {
        if (head == null || head.next == null)
            return head;

        SingleNode pre = null;
        SingleNode next;
        while (head != null) {
            next = head.next;
            head.next = pre;

            pre = head;
            head = next;
        }
        head = pre;
        return head;
    }

    private static DoubleNode reverseLinkedList(DoubleNode head) {
        if (head == null || head.next == null)
            return head;

        DoubleNode pre = null;
        DoubleNode next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            head.prev = next;

            pre = head;
            head = next;
        }
        return pre;
    }

    // 单链表的 Node
    public static class SingleNode {
        SingleNode next;
        int value;

        public SingleNode(int value) {
            this.value = value;
        }
    }

    // 双向链表 Node
    public static class DoubleNode {
        DoubleNode next;
        DoubleNode prev;
        int value;

        public DoubleNode(int value) {
            this.value = value;
        }
    }

    private static void printLinkedList(SingleNode h) {
        SingleNode n = h;
        while (n != null) {
            System.out.println(n.value);
            n = n.next;
        }
    }

    private static void printLinkedList(DoubleNode h) {
        DoubleNode n = h;
        while (n != null) {
            System.out.println(n.value);
            n = n.next;
        }
    }
}
