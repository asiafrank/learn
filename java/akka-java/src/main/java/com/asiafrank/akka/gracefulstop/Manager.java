package com.asiafrank.akka.gracefulstop;

import akka.actor.*;
import akka.japi.Creator;
import akka.japi.Procedure;

/**
 * Manager
 * <p>
 * </p>
 * Created at 19/12/2016.
 *
 * @author asiafrank
 */
class Manager extends UntypedActor {
    static final String SHUTDOWN = "shutdown";

    private ActorRef actorRef;

    private ActorRef worker;

    @Override
    public void preStart() throws Exception {
        worker = getContext().watch(actorRef);
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message.equals("job")) {
            System.out.println("job");
            worker.tell("crunch", getSelf());
        } else if (message.equals(SHUTDOWN)) {
            System.out.println("poisonPill");
            worker.tell(PoisonPill.getInstance(), getSelf());
            getContext().become(shuttingDown);
        } else {
            unhandled(message);
        }
    }

    Procedure<Object> shuttingDown = new Procedure<Object>() {
        @Override
        public void apply(Object param) throws Exception {
            if (param.equals("job")) {
                System.out.println("job shuttingDown...");
                getSender().tell("service unavailable, shutting down", getSelf());
            } else if (param instanceof Terminated) {
                System.out.println("shuttingDown Terminated: " + ((Terminated) param).actor().path());
                getContext().stop(getSelf());
            }
        }
    };

    private Manager(ActorRef actorRef) {
        this.actorRef = actorRef;
    }

    public static Props props(final ActorRef actorRef) {
        return Props.create(new Creator<Actor>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Actor create() throws Exception {
                return new Manager(actorRef);
            }
        });
    }
}
