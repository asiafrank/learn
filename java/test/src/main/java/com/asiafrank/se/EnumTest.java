package com.asiafrank.se;

import org.omg.IOP.ENCODING_CDR_ENCAPS;

/**
 * Created by asiafrank on 1/2/2016.
 */
public enum EnumTest {
    ENUM_TEST(1, "message");

    int code;
    String msg;

    EnumTest(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
