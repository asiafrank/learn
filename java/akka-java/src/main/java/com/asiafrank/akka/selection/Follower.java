package com.asiafrank.akka.selection;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;

/**
 * Follower - Identifying Actors via Actor Selection
 * <p>
 * Created by zhangxf on 12/5/2016.
 */
class Follower extends UntypedActor {
    final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    final String identifyId = "1";

    {
        log.info("selection");
        ActorSelection selection = getContext().actorSelection("/user/another");

        // get ref by message
        selection.tell(new Identify(identifyId), getSelf());

        // getRef by future
        // Future<ActorRef> future = selection.resolveOne(FiniteDuration.apply(10, TimeUnit.MILLISECONDS));
    }

    ActorRef another;

    final ActorRef probe;

    private Follower(ActorRef probe) {
        this.probe = probe;
    }

    public static Props props(final ActorRef probe) {
        return Props.create(new Creator<Actor>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Actor create() throws Exception {
                return new Follower(probe);
            }
        });
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof ActorIdentity) {
            ActorIdentity identity = (ActorIdentity) message;
            if (identity.correlationId().equals(identifyId)) {
                ActorRef ref = identity.getRef();
                if (ref == null) {
                    getContext().stop(getSelf());
                } else {
                    another = ref;
                    getContext().watch(another);
                    probe.tell(ref, getSelf());
                }
            }
        } else if (message instanceof Terminated) {
            final Terminated t = (Terminated) message;
            if (t.getActor().equals(another)) {
                getContext().stop(getSelf());
            }
        } else {
            unhandled(message);
        }
    }
}
