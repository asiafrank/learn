package labuladong;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 用栈实现队列
 * 用队列实现栈
 * @author zhangxiaofan 2020/12/14-17:20
 */
public class QueueAndStack {

    /**
     * 用栈实现队列
     */
    static class Queue {
        Deque<Integer> stack1 = new ArrayDeque<>();
        Deque<Integer> stack2 = new ArrayDeque<>();

        /**
         * 放入 stack1 中
         */
        public void push(Integer x) {
            stack1.push(x);
        }

        /**
         * 如果 stack2 不为空，则弹出
         * 如果 stack2 为空，则将 stack1 里的全弹出，放入 stack2 后，再弹出
         */
        public Integer pop() {
            peek();
            return stack2.pop();
        }

        /**
         * 队头元素
         * 如果 stack2 不为空，则peek
         * 如果 stack2 为空，则将 stack1 里的全弹出，放入 stack2 后，再peek
         */
        public Integer peek() {
            if (stack2.isEmpty()) {
                while (!stack1.isEmpty()) {
                    Integer x = stack1.pop();
                    stack2.push(x);
                }
            }

            return stack2.peek();
        }

        public boolean isEmpty() {
            return stack1.isEmpty() && stack2.isEmpty();
        }
    }

    /**
     * 队列实现栈
     */
    static class Stack {

        java.util.Queue<Integer> queue = new LinkedList<>();
        Integer topElement = 0;

        public void push(Integer x) {
            queue.add(x);
            topElement = x;
        }

        public Integer peek() {
            return topElement;
        }

        /**
         * 将除队尾的元素（也就是最近添加的元素）外的元素都重新加入到队列中去
         * 现在队头就是最近添加的元素，弹出队头，
         */
        public Integer pop() {
            int size = queue.size();
            while (size > 1) {
                Integer x = queue.poll();
                queue.add(x);
                size--;
            }
            Integer pop = queue.poll();
            topElement = queue.peek();
            return pop;
        }
    }

    public static void main(String[] args) {
        Stack s = new Stack();
        s.push(1);
        s.push(2);
        s.push(3);
        System.out.println(s.pop());
        System.out.println(s.pop());
        System.out.println(s.pop());

        System.out.println("--------------");

        Queue q = new Queue();
        q.push(1);
        q.push(2);
        q.push(3);
        q.push(4);
        System.out.println(q.pop());
        System.out.println(q.pop());
        System.out.println(q.pop());
        System.out.println(q.pop());
    }
}
