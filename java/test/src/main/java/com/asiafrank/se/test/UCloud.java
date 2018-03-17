package com.asiafrank.se.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UCloud {
    public static void main(String[] args) {
        String word = "UCanUup";
        String text = getText();

        int resultA = normalCount(text, word);
        int resultB = RegExCount(text, word);
//        int resultC = KMPCount(text, word);

        System.out.println("Result:\nA=" + resultA + "\nB=" + resultB);
//        System.out.println("Result:\nA=" + resultA + "\nB=" + resultB + "\nC=" + resultC);
    }

    private static int normalCount(String text, String word) {
        int count = 0;
        String[] words = text.split(" ");
        for (String w : words) {
            if (w.equals(word)) {
                count++;
            }
        }
        return count;
    }

    private static int RegExCount(String text, String word) {
        int count = 0;
        Pattern pattern = Pattern.compile(word);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    /**
     * Get UCloud.txt String from Network.
     *
     * @return String of UCloud.txt
     */
    private static String getText() {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL("http://106.75.28.160/UCloud.txt");
            URLConnection conn = url.openConnection();
            try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                System.err.println("BufferedReader Error: " + e);
            }
        } catch (IOException e) {
            System.err.println("Connection Error: " + e);
        }
        return sb.toString();
    }
}
