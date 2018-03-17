package com.asiafrank.akka.fsm;

import java.util.List;

/**
 * Batch
 * <p>
 * </p>
 * Created at 1/11/2017.
 *
 * @author zhangxf
 */
public final class Batch {
    final List<Object> objectList;

    public Batch(List<Object> objectList) {
        this.objectList = objectList;
    }
}
