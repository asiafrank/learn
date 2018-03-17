package com.asiafrank.util.nmap;

import com.asiafrank.util.nmap.entity.Address;
import com.asiafrank.util.nmap.entity.Host;
import com.asiafrank.util.nmap.entity.NmapResult;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

/**
 * NmapTest
 * <p>
 * </p>
 * Created at 12/12/2016.
 *
 * @author zhangxf
 */
public class NmapTest {
    @Test
    public void parseTest() throws IOException {
        String p = JacksonXmlTest.class.getClassLoader().getResource("output-3.xml").getFile();
        File f = new File(p);
        NmapResult nmapResult = NmapParser.parse(f);
        assertEquals("nmap -sn -oX - 10.1.11.0/24", nmapResult.getArgs());
    }

    @Test
    public void parseTest2() throws IOException {
        String p = JacksonXmlTest.class.getClassLoader().getResource("1.xml").getFile();
        File f = new File(p);
        NmapResult nmapResult = NmapParser.parse(f);
        LinkedList<Host> hosts = nmapResult.getHosts();
        for (Host h : hosts) {
            LinkedList<Address> addresses = h.getAddress();
            if (!addresses.isEmpty())
                System.out.println(addresses.get(0).getAddr());
        }
    }
}
