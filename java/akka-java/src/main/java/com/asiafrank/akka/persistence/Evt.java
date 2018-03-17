package com.asiafrank.akka.persistence;

import java.io.Serializable;

/**
 * Evt
 * <p>
 * </p>
 * Created at 1/17/2017.
 *
 * @author zhangxf
 */
public final class Evt implements Serializable {
    private final static long serialVersionUID = 1L;
    private final String data;

    public Evt(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Evt{" +
                "data='" + data + '\'' +
                '}';
    }
}
