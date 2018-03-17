package com.asiafrank.learn.io;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import static net.mindview.util.Print.print;
import static net.mindview.util.Print.printnb;

public class LargeMappedFiles {
    private static int length = 0x8FFFFFF; // 128 MB
    public static void main(String[] args) throws Exception {
        String fileName = "src/com/asiafrank/learn/io/test.dat";
        MappedByteBuffer out = new RandomAccessFile(fileName, "rw")
                .getChannel().map(FileChannel.MapMode.READ_WRITE, 0, length);
        for (int i = 0; i < length; i++) {
            out.put((byte)'x');
        }
        print("Finished writing");

        for (int i = length / 2; i < length / 2 + 6; i++) {
            printnb((char)out.get(i));
        }
    }
}
