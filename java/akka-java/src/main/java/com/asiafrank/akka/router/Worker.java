package com.asiafrank.akka.router;

import akka.actor.Actor;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;

/**
 * Worker
 * <p>
 * </p>
 * Created at 1/10/2017.
 *
 * @author zhangxf
 */
final class Worker extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Work) {
            System.out.println(getSelf().path() + "-" + message.toString());
        } else {
            unhandled(message);
        }
    }

    private Worker() {
    }

    static Props props() {
        return Props.create(new Creator<Actor>() {
            private final static long serialVersionUID = 1L;
            @Override
            public Actor create() throws Exception {
                return new Worker();
            }
        });
    }
}
