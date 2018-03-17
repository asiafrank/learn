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
final class Workers2 extends UntypedActor {
    @Override
    public void preStart() throws Exception {
        ActorRef w5 = getContext().actorOf(Props.create(Worker.class), "w5");
        ActorRef w6 = getContext().actorOf(Props.create(Worker.class), "w6");
        ActorRef w7 = getContext().actorOf(Props.create(Worker.class), "w7");
        System.out.println("worker1-" + w5.path());
        System.out.println("worker1-" + w6.path());
        System.out.println("worker1-" + w7.path());
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        System.out.println("do nothing");
    }
}
