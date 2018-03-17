package com.asiafrank.akka.persistence;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * ExampleState
 * <p>
 * </p>
 * Created at 1/17/2017.
 *
 * @author zhangxf
 */
public final class ExampleState implements Serializable {
    private final static long serialVersionUID = 1L;
    private final ArrayList<String> events;

    public ExampleState(ArrayList<String> events) {
        this.events = events;
    }

    public ExampleState() {
        this(new ArrayList<>());
    }

    public ExampleState copy() {
        return new ExampleState(events);
    }

    public void update(Evt evt) {
        events.add(evt.getData());
    }

    public int size() {
        return events.size();
    }

    @Override
    public String toString() {
        return events.toString();
    }
}
