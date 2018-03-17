package com.asiafrank.akka.router;

import java.io.Serializable;

/**
 * Work
 * <p>
 * </p>
 * Created at 1/10/2017.
 *
 * @author zhangxf
 */
final class Work implements Serializable {
    private final static long serialVersionUID = 1L;
    private final String payload;

    Work(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Work{" +
                "payload='" + payload + '\'' +
                '}';
    }
}
