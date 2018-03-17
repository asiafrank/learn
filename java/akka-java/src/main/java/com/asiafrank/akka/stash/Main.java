package com.asiafrank.akka.stash;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * Main
 * <p>
 * </p>
 * Created at 12/20/2016.
 *
 * @author zhangxf
 */
class Main {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.apply("system");
        ActorRef actorWithProtocol = system.actorOf(ActorWithProtocol.props()
                .withMailbox("bounded-mailbox"), "actor-with-protocol");
        actorWithProtocol.tell("stash", ActorRef.noSender());
        actorWithProtocol.tell("writea", ActorRef.noSender());
        actorWithProtocol.tell("closeb", ActorRef.noSender());
        actorWithProtocol.tell("unstash", ActorRef.noSender());
        actorWithProtocol.tell("write", ActorRef.noSender());
        actorWithProtocol.tell("close", ActorRef.noSender());
    }
}
