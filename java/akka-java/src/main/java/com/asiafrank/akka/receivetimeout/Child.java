package com.asiafrank.akka.receivetimeout;

import akka.actor.Actor;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;

/**
 * Child
 * <p>
 * Created by zhangxf on 12/7/2016.
 */
class Child extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props() {
        return Props.create(new Creator<Actor>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Actor create() throws Exception {
                return new Child();
            }
        });
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        log.info("start");
        if (message.equals("start")) {
            Thread.sleep(3000);
            getSender().tell("end", getSelf());
        }
        log.info("end");
    }
}
