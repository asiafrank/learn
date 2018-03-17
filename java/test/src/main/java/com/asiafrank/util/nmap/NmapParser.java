package com.asiafrank.util.nmap;

import com.asiafrank.util.nmap.entity.NmapResult;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;

/**
 * NmapParser
 * Example:
 * <p>
 * CommandBuilder cmd = CommandBuilder.newInstance("");
 * cmd.addOptions(Options.A, Options.oX());
 * cmd.addTargets("192.168.0.0/24");
 * String s = Nmap.newInstance(cmd).call();
 * NmapResult nmaprun = NmapParser.parse(s);
 * </p>
 * Created at 2016/12/10.
 *
 * @author zhangxf
 */
public class NmapParser {
    public static NmapResult parse(String nmapXml) throws IOException {
        String src = format(nmapXml);
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        return xmlMapper.readValue(src, NmapResult.class);
    }

    public static NmapResult parse(File file) throws IOException {
        String xml = new String(Files.readAllBytes(file.toPath()));
        return parse(xml);
    }

    private static String format(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException();
        }

        BufferedReader reader = new BufferedReader(new StringReader(input));
        StringBuilder result = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("<task")) continue;
                result.append(line.trim());
            }
            return result.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
