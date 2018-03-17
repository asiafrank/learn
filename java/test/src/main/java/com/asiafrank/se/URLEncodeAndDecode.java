package com.asiafrank.se;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLEncodeAndDecode {
    public static void main(String[] args) {
        String url = "http://cdn.jiaxihezi.com/bn/eac924ab83dcdc4fd7a54f81ca5889cc.jpg";
        try {
            System.out.println(URLEncoder.encode(url, "UTF-8"));
            System.out.println(URLDecoder.decode(url, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
