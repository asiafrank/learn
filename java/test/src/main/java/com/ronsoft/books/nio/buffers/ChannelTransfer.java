package com.ronsoft.books.nio.buffers;

import java.io.FileInputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Test channel transfer. This is a very simplistic concatenation
 * program. It takes a list of file names as arguments, opens each
 * in turn and transfers (copies) their content to the given
 * WritableByteChannel (in this case, stdout).
 *
 * Created April 2002
 * @author Ron Hitchens (ron@ronsoft.com)
 */
public class ChannelTransfer {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("Usage: filename ...");
            return;
        }

        catFiles(Channels.newChannel(System.out), args);
    }

    // Concatenate the content of each of the named files to
    // the given channel. A very dumb version of 'cat'.
    private static void catFiles(WritableByteChannel target,
                                 String[] files)
        throws Exception
    {
        for (int i = 0; i < files.length; i++) {
            FileInputStream fis = new FileInputStream(files[i]);
            FileChannel channel = fis.getChannel();

            channel.transferTo(0, channel.size(), target);

            channel.close();
            fis.close();
        }
    }
}
