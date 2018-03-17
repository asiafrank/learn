package com.asiafrank.se;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Xiaofan Zhang on 7/4/2016.
 */
public class FileTest {
    private static final String BASE_PATH = "/data/";

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString());

        String folder = "/data/logs/dd";
        String[] arr = folder.split("/");
        for (String s : arr) {
            if (s != null && !s.equals("")) {
                System.out.println("-" + s);
            }
        }

        System.out.println("/".equals(File.separator));

        System.out.println(folder.replaceAll("/", File.separator));

        System.out.println(new File(folder).exists());

        System.out.println(getCharAndNum(5));
    }

    public static String getCharAndNum(int length) {
        String val = "";

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

            if ("char".equalsIgnoreCase(charOrNum)) { // 字符串
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
                val += String.valueOf(random.nextInt(10));
            }
        }

        return val;
    }
}
