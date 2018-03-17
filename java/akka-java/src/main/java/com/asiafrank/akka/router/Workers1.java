package com.asiafrank.akka.router;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * Workers1
 * <p>
 * </p>
 * Created at 1/10/2017.
 *
 * @author zhangxf
 */
final class Workers1 extends UntypedActor {
    @Override
    public void preStart() throws Exception {
        ActorRef w1 = getContext().actorOf(Props.create(Worker.class), "w1");
        ActorRef w2 = getContext().actorOf(Props.create(Worker.class), "w2");
        ActorRef w3 = getContext().actorOf(Props.create(Worker.class), "w3");
        System.out.println("worker1-" + w1.path());
        System.out.println("worker1-" + w2.path());
        System.out.println("worker1-" + w3.path());
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        System.out.println("do nothing");
    }
}
