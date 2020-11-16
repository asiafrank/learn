package coursex;


import java.util.Stack;

public class C12ReverseStack {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.add(1);
        stack.add(2);
        stack.add(3);
        reverseStack(stack);

        for (Integer i : stack) {
            System.out.println(i);
        }
    }

    /**
     * 1.每踢出一个栈底
     * 2.再调用 reverseStack 下一层继续
     * 3.将这个栈底塞入 stack (最外面一层的方法，是栈顶)
     */
    private static void reverseStack(Stack<Integer> stack) {
        if (stack.isEmpty())
            return;

        Integer i = f(stack);
        reverseStack(stack);
        stack.add(i);
    }

    // 将底踢出栈，并最终返回
    private static Integer f(Stack<Integer> stack) {
        Integer pop = stack.pop();
        if (stack.isEmpty())
            return pop;

        Integer bottom = f(stack);
        stack.add(pop);
        return bottom;
    }
}
