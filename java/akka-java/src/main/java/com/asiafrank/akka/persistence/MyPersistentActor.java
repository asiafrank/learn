package com.asiafrank.akka.persistence;

import akka.actor.ActorRef;
import akka.japi.Procedure;
import akka.persistence.UntypedPersistentActor;

/**
 * MyPersistentActor
 * <p>
 * </p>
 * Created at 1/19/2017.
 *
 * @author zhangxf
 */
public final class MyPersistentActor extends UntypedPersistentActor {
    @Override
    public String persistenceId() {
        return "some-persistent-id";
    }

    @Override
    public void onReceiveRecover(Object msg) throws Throwable {
        // handle recovery
    }

    @Override
    public void onReceiveCommand(Object msg) throws Throwable {
        System.out.println("onReceiveCommand invoked: " + msg);
        if (msg.equals("stop")) {
            getContext().stop(getSelf());
            return;
        }

        sender().tell(msg, getSelf());

        final Procedure<String> replyToSender = new Procedure<String>() {
            @Override
            public void apply(String param) throws Exception {
                System.out.println("async apply: " + param);
                sender().tell(param, getSelf());
            }
        };
        persistAsync(String.format("evt-%s-1", msg), replyToSender);
        persistAsync(String.format("evt-%s-2", msg), replyToSender);
        deferAsync(String.format("evt-%s-3", msg), replyToSender);
    }
}
