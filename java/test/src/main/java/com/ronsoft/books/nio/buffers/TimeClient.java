package com.ronsoft.books.nio.buffers;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Request time service, per RFC 868. RFC 868
 * (http://www.ietf.org/rfc/rfc0868.txt) is a verify simple time protocol
 * whereby one system can request the current time from another system
 * Most Linux, BSD and Solaris systems provide RFC 868 time service
 * on port 37. This simple program will inter-operate with those.
 * The National Institute of Standards and Technology (NIST) operates
 * a public time server at time.nist.gov.
 *
 * The RFC 868 protocal specifies a 32 bit unsigned value be sent,
 * representing the number of seconds since Jan 1, 1900. The Java
 * epoch begins on Jan 1, 1970 (same as unix) so an adjustment is
 * made by adding or subtracting 2,208,988,800 as appropriate. To
 * avoid shifting and masking, a four-byte slice of an
 * eight-byte buffer is used to send/receive. But getLong()
 * is done on the full eight bytes to get a long value.
 *
 * When run, this program will issue time requests to each hostname
 * given on the command line, then enter a loop to receive packets.
 * Note that some requests or replies may be lost, which means this
 * code could block forever.
 *
 * @author Ron Hitchens (ron@ronsoft.com)
 */
public class TimeClient {
    private static final int DEFAULT_TIME_PORT = 37;
    private static final long DIFF_1900 = 2208988800L;

    protected int port = DEFAULT_TIME_PORT;
    protected List remoteHosts;
    protected DatagramChannel channel;

    public TimeClient(String[] args) throws Exception {
        if (args.length == 0) {
            throw new Exception("Usage: [-p port] host ...");
        }

        parseArgs(args);

        this.channel = DatagramChannel.open();
    }

    protected InetSocketAddress receivePacket(DatagramChannel channel,
                                              ByteBuffer buffer)
        throws Exception
    {
        buffer.clear();

        // Receive an unsigned 32-bite, big-endian value
        return ((InetSocketAddress)channel.receive(buffer));
    }

    // Send time requests to all the supplied hosts
    protected void sendRequests() throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        Iterator it = remoteHosts.iterator();

        while (it.hasNext()) {
            InetSocketAddress sa = (InetSocketAddress)it.next();
            System.out.println("Requesting time from " + sa.getHostName() + ":" + sa.getPort());

            // Make it empty (see RFC868)
            buffer.clear().flip();
            //Fire and forget
            channel.send(buffer, sa);
        }
    }

    // Receive any replies that arrive
    public void getReplies() throws Exception {
        // Allocate a buffer to hold a long value
        ByteBuffer longBuffer = ByteBuffer.allocate(8);

        // Assure big-endian (network) byte order
        longBuffer.order(ByteOrder.BIG_ENDIAN);
        // Zero the whole buffer to be sure
        longBuffer.putLong(0, 0);
        // Position to first byte of the low-order 32 bits
        longBuffer.position(4);

        // Slice the buffer; gives view of the low-order 32 bits
        ByteBuffer buffer = longBuffer.slice();
        int expect = remoteHosts.size();
        int replies = 0;

        System.out.println("");
        System.out.println("Waiting for replies...");

        while (true) {
            InetSocketAddress sa;
            sa = receivePacket(channel, buffer);

            buffer.flip();
            replies++;

            printTime(longBuffer.getLong(0), sa);

            if (replies == expect) {
                System.out.println("All packets answered");
                break;
            }

            // Some replies haven't shown up yet
            System.out.println("Received " +
                replies + " of " + expect + " replies");
        }
    }

    // Print info about a reveived time reply
    protected void printTime(long remote1900, InetSocketAddress sa) {
        // Local time as seconds sine Jan 1, 1970
        long local = System.currentTimeMillis() / 1000;

        // Remote time as seconds since Jan 1, 1970
        long remote = remote1900 - DIFF_1900;
        Date remoteDate = new Date(remote * 1000);
        Date localDate = new Date(local * 1000);
        long skew = remote - local;

        System.out.println("Reply from " + sa.getHostName() + ":" + sa.getPort());
        System.out.println("  there: " + remoteDate);
        System.out.println("  here: " + localDate);
        System.out.print(" skew: ");

        if (skew == 0) {
            System.out.println("none");
        } else if (skew > 0) {
            System.out.println(skew + " seconds ahead");
        } else {
            System.out.println(-skew + " seconds behind");
        }
    }

    protected void parseArgs(String[] args) {
        remoteHosts = new LinkedList<>();

        for (int i = 0; i < args.length; i++) {
            String arg= args[i];

            // Send client requests to the given port
            if (arg.equals("-p")) {
                i++;
                this.port = Integer.parseInt(args[i]);
                continue;
            }

            // Create an address object for the hostname
            InetSocketAddress sa = new InetSocketAddress(arg, port);

            // Validate that it has an address
            if (sa.getAddress() == null) {
                System.out.println("Cannot resolve address: " + arg);
                continue;
            }

            remoteHosts.add(sa);
        }
    }

    // ---------------------------------------------------------------

    public static void main(String[] args) throws Exception {
        TimeClient client = new TimeClient(args);

        client.sendRequests();
        client.getReplies();
    }
}
