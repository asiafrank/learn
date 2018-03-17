package com.asiafrank.akka.stash;

import akka.actor.Actor;
import akka.actor.Props;
import akka.actor.UntypedActorWithStash;
import akka.japi.Creator;
import akka.japi.Procedure;

/**
 * ActorWithProtocol
 * <p>
 * </p>
 * Created at 12/20/2016.
 *
 * @author zhangxf
 */
class ActorWithProtocol extends UntypedActorWithStash {

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message.equals("stash")) {
            System.out.println("receive0 " + message);
            stash();
        } else if (message.equals("unstash")) {
            System.out.println("receive1 " + message);
            unstashAll();
            getContext().become(write, false); // add behavior on top instead of replacing
        } else if (message instanceof String) {
            System.out.println("receive2 " + message);
        } else {
            unhandled(message);
        }
    }

    private Procedure<Object> write = param -> {
        if (param.equals("write")) {
            System.out.println("procedure0 " + param);
        } else if (param.equals("close")) {
            System.out.println("procedure1 " + param);
            unstashAll();
            getContext().unbecome();
        } else {
            System.out.println("procedure2 " + param);
            stash();
        }
    };

    private ActorWithProtocol() {}

    public static Props props() {
        return Props.create(new Creator<Actor>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Actor create() throws Exception {
                return new ActorWithProtocol();
            }
        });
    }
}
