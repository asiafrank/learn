package com.asiafrank.learn.springboot.filter;

import java.util.regex.Pattern;

/**
 * Created by JiangTao on 2018/1/17.
 */
public class InjectionBlocker {

    private enum RULE {
        COMMON_REGEX(".*['<>].*"),

        /* Block SQL injections */
        SQL_BLOCK_REGEX01("(cost\\()|(concat\\()"),
        SQL_BLOCK_REGEX02("[+|(%20)]union[+|(%20)]"),
        SQL_BLOCK_REGEX03("[+|(%20)]and[+|(%20)]"),
        SQL_BLOCK_REGEX04("[+|(%20)]or[+|(%20)]"),
        SQL_BLOCK_REGEX05("[+|(%20)]select[+|(%20)]"),
        SQL_BLOCK_REGEX06("[+|(%20)|(%2C)|(28%)]select[+|(%20)|(%2C)|(%28)|(%29)]"),
        SQL_BLOCK_REGEX07("(cost\\()|(concat\\()"),
        SQL_BLOCK_REGEX08("[+|(%20)]union[+|(%20)]"),
        SQL_BLOCK_REGEX09("[+|(%20)]and[+|(%20)]"),
        SQL_BLOCK_REGEX10("[+|(%20)]or[+|(%20)]"),
        SQL_BLOCK_REGEX11("[+|(%20)]select[+|(%20)]"),
        SQL_BLOCK_REGEX12("[+|(%20)|(%2C)|(28%)]select[+|(%20)|(%2C)|(%28)|(%29)]"),
        SQL_BLOCK_REGEX13("union.*select.*\\("),
        SQL_BLOCK_REGEX14("union.*all.*select.*"),
        SQL_BLOCK_REGEX15("concat.*\\("),
        SQL_BLOCK_REGEX16("union.*select.*\\("),
        SQL_BLOCK_REGEX17("union.*all.*select.*"),
        SQL_BLOCK_REGEX18("concat.*\\("),

        /* Block file injections */
        FILE_BLOCK_REGEX01("[a-zA-Z0-9_]=http://"),
        FILE_BLOCK_REGEX02("[a-zA-Z0-9_]=(\\.\\.//?)+"),
        FILE_BLOCK_REGEX03("[a-zA-Z0-9_]=/([a-z0-9_.]//?)+");

        private String regex;

        RULE(String regex) {
            this.regex = regex;
        }
    }

    public static boolean match(String str) {
        for (RULE r : RULE.values()) {
            Pattern pattern = Pattern.compile(r.regex, Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(str).find()) {
                return true;
            }
        }
        return false;
    }
}