package com.asiafrank.akka.example1;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;

/**
 * Lifecycle Monitoring aka DeathWatch
 * <p>
 * Created by zhangxf on 12/5/2016.
 */
class WatchActor extends UntypedActor {
    final ActorRef child = this.getContext().actorOf(Props.empty(), "child");

    {
        this.getContext().watch(child); // <-- the only call needed for registration
    }

    ActorRef lastSender = getContext().system().deadLetters();

    @Override
    public void onReceive(Object message) {
        if (message.equals("kill")) {
            getContext().stop(child);
            lastSender = getSender();
        } else if (message instanceof Terminated) {
            final Terminated t = (Terminated) message;
            if (t.getActor() == child) {
                lastSender.tell("finished", getSelf());
            }
        } else {
            unhandled(message);
        }
    }
}