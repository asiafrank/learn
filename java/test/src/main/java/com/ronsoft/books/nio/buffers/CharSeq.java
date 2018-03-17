package com.ronsoft.books.nio.buffers;

import java.nio.CharBuffer;

/**
 * Demonstrate behavior of java.lang.CharSequence as implemented
 * by String, StringBuffer and CharBuffer.
 *
 * @author Ron Hitchens (ron@ronsoft.com)
 */
public class CharSeq {
    public static void main(String[] args) {
        StringBuffer stringBuffer = new StringBuffer("Hello World");
        CharBuffer charBuffer = CharBuffer.allocate(20);
        CharSequence charSequence = "Hello World";

        // Derived directly from a String
        printCharSequence(charSequence);

        // Derived from a StringBuffer
        charSequence = stringBuffer;
        printCharSequence(charSequence);

        // Change StringBuffer
        stringBuffer.setLength(0);
        stringBuffer.append("Goodbye cruel world");
        // Same "immutable" CharSequence yields different result
        printCharSequence(charSequence);

        // Derive CharSequence from CharBuffer
        charSequence = charBuffer;
        charBuffer.put("xxxxxxxxxxxxxxxxxxxx");
        charBuffer.clear();

        charBuffer.put("Hello World");
        charBuffer.flip();
        printCharSequence(charSequence);

        charBuffer.mark();
        charBuffer.put("Seeya");
        charBuffer.reset();
        printCharSequence(charSequence);

        charBuffer.clear();
        printCharSequence(charSequence);
        // Changing underlying CharBuffer is reflected in the
        // read-only CharSequence interface
    }

    private static void printCharSequence(CharSequence cs) {
        System.out.println("length=" + cs.length() + ", content='" + cs.toString() + "'");
    }
}
