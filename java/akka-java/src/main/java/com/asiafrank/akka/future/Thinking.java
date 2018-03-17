package com.asiafrank.akka.future;

import akka.actor.Actor;
import akka.actor.Props;
import akka.actor.Status;
import akka.actor.UntypedActor;
import akka.japi.Creator;

/**
 * Thinking
 * <p>
 * Created by zhangxf on 12/6/2016.
 */
class Thinking extends UntypedActor {

    public static Props props() {
        return Props.create(new Creator<Actor>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Actor create() throws Exception {
                return new Thinking();
            }
        });
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof String) {
            try {
                String result = operation((String) message);
                getSender().tell(result, getSelf());
            } catch (Exception e) {
                getSender().tell(new Status.Failure(e), getSelf());
                throw e;
            }
        }
    }

    private String operation(String input) {
        // do something
        return input;
    }
}
