package com.asiafrank.akka.gracefulstop;

import akka.actor.Actor;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;

/**
 * Crunch
 * <p>
 * </p>
 * Created at 19/12/2016.
 *
 * @author asiafrank
 */
class Crunch extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Throwable {
        if (message.equals("crunch")) {
            System.out.println("Crunch...");
        } else {
            unhandled(message);
        }
    }

    private Crunch() {}

    public static Props props() {
        return Props.create(new Creator<Actor>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Actor create() throws Exception {
                return new Crunch();
            }
        });
    }
}
