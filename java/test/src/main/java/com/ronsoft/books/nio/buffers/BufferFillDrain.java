package com.ronsoft.books.nio.buffers;

import java.nio.CharBuffer;

public class BufferFillDrain {

    private static int index = 0;

    private static String[] strings = {
        "A random string value",
        "The product of an infinite number of monkeys",
        "hey hey we're the monkees",
        "Opening act for the monkees: Jimi Hendrix",
        "'Scuse me while I kiss this fly",
        "Help Me! Help Me!"
    };

    private static void drainBuffer(CharBuffer buffer) {
        while (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }
        System.out.println("");
    }

    private static boolean fillBuffer(CharBuffer buffer) {
        if (index >= strings.length) {
            return false;
        }

        String str = strings[index++];
        for (int i = 0; i < str.length(); i++) {
            buffer.put(str.charAt(i));
        }
        return true;
    }

    public static void main(String[] args)
            throws Exception
    {
        CharBuffer buffer = CharBuffer.allocate(100);
        while (fillBuffer(buffer)) {
            buffer.flip();
            drainBuffer(buffer);
            buffer.clear();
        }
    }
}
