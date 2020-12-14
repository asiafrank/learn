package coursex;

import com.asiafrank.util.Printer;

import java.util.ArrayDeque;
import java.util.Deque;

public class C12ReverseStack2 {

    /**
     * 递归，把栈底去除并返回
     */
    public static Integer f(Deque<Integer> stack) {
        Integer pop = stack.pop();
        if (stack.isEmpty()) {
            return pop;
        }

        Integer last = f(stack);
        stack.push(pop);
        return last;
    }

    /**
     * 每次都只把一个栈底去除并返回，直到所有步骤都完成
     */
    public static void reverseStack(Deque<Integer> stack) {
        if (stack.isEmpty())
            return;

        Integer last = f(stack);
        reverseStack(stack);
        stack.push(last);
    }

    public static void main(String[] args) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        Printer.printStackWithNoChange(stack);
        reverseStack(stack);
        Printer.printStackWithNoChange(stack);
    }
}
