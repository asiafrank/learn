package com.asiafrank.bplan.downloader;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Downloader Main
 *
 * @author zhangxf created at 9/5/2018.
 */
public class Main {
    public static void main(String[] args) {
        final var name = "downloader";
        System.out.println("Hello " + name);

        String encoded = URLEncoder.encode("http:://www.abc.com?keyword=中文", StandardCharsets.UTF_8);
        String decoded = URLDecoder.decode(encoded, StandardCharsets.UTF_8);

        System.out.println("encoded : " + encoded);
        System.out.println("decoded : " + decoded);
    }
}
