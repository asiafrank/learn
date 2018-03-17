package com.asiafrank.learn.spring.akka.actor;

import akka.actor.UntypedActor;
import com.asiafrank.learn.spring.akka.model.Result;
import com.asiafrank.learn.spring.akka.model.Status;

/**
 * Task -
 * <p>
 * </p>
 * Created at 12/12/2016.
 *
 * @author asiafrank
 */
public class Task extends UntypedActor {
    private String id;

    private Status status;

    private Result result;

    public static enum Msg {
        START, DONE;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message == Msg.START) {
            // do something
        } else {
            unhandled(message);
        }
    }
}
