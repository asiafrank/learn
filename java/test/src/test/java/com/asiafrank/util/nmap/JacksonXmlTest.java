package com.asiafrank.util.nmap;

import com.asiafrank.util.nmap.sample.Father;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.Test;

import java.io.File;

/**
 * Example - Created at 12/8/2016.
 * <p>
 * </p>
 *
 * @author zhangxf
 */
public class JacksonXmlTest {

    @Test
    public void xmlParserTest() throws Exception {
        // Parse xml to Entity
        String p = JacksonXmlTest.class.getClassLoader().getResource("father.xml").getFile();
        File f = new File(p);

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        Father father = xmlMapper.readValue(f, Father.class);
        System.out.print(father.toString());
    }
}
