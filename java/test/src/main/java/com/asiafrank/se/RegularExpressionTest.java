package com.asiafrank.se;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpressionTest {
    StringBuffer stringBuffer = new StringBuffer("good");
    char[] ch = { 'a', 'b', 'c' };

    public static void main(String args[]) {
        RegularExpressionTest ex = new RegularExpressionTest();
        ex.change(ex.stringBuffer, ex.ch);
        System.out.print(ex.stringBuffer + " and ");
        System.out.println(ex.ch);

        System.out.println("***** regex ****");
        // String to be scanned to find the pattern.
        String line = "http://www.xxxxx.com/weeerr/h5/weeef/weeffd";
        String line2 = "http://www.xxxxx.com/h5/weee";
        String pattern = "^http://www.xxxxx.com(/{0,1})(\\S*)/h5/(\\S*)*";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(line2);
        if (m.find()) {
            System.out.println("Found value: " + m.group(0));
            System.out.println("Found value: " + m.group(1));
            System.out.println("Found value: " + m.group(2));
            System.out.println("Found value: " + m.group(3));
        } else {
            System.out.println("NO MATCH");
        }

    }

    public void change(StringBuffer stringBuffer, char ch[]) {
        stringBuffer.append("ppppp");
        ch[0] = 'g';
    }

    //Constructors


    public RegularExpressionTest() {
    }

    public RegularExpressionTest(StringBuffer stringBuffer, char[] ch) {
        this.stringBuffer = stringBuffer;
        this.ch = ch;
    }
}
