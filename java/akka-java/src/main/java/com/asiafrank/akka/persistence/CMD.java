package com.asiafrank.akka.persistence;

import java.io.Serializable;

/**
 * CMD
 * <p>
 * </p>
 * Created at 1/16/2017.
 *
 * @author zhangxf
 */
public final class CMD implements Serializable {
    private final static long serialVersionUID = 1L;
    private final String data;

    public CMD(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "CMD{" +
                "data='" + data + '\'' +
                '}';
    }
}
