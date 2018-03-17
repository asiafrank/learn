package com.asiafrank.akka.example1;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.Props;

/**
 * Main
 * <p>
 * Created by zhangxf on 12/5/2016.
 */
class Main {
    public static void main(String[] args){
        final ActorSystem system = ActorSystem.apply();
        final ActorRef untyped = system.actorOf(Props.create(MyUntypedActor.class), "untyped");
        Inbox inbox = Inbox.create(system);
        inbox.send(untyped, "hello"); // outside send message to actor

        final ActorRef demo = system.actorOf(DemoActor.props(10), "demo");
        untyped.tell("morning", demo); // actor send message to another actor
    }
}
