package com.asiafrank.akka.selection;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;

/**
 * Probe
 * <p>
 * Created by zhangxf on 12/5/2016.
 */
class Probe extends UntypedActor {
    final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props() {
        return Props.create(new Creator<Actor>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Actor create() throws Exception {
                return new Probe();
            }
        });
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof ActorRef) {
            ActorRef ref = (ActorRef) message;
            log.info("find actor: {}", ref.path());
        } else {
            unhandled(message);
        }
    }
}
