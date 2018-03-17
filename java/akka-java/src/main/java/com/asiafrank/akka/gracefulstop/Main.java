package com.asiafrank.akka.gracefulstop;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * Main
 * <p>
 * </p>
 * Created at 19/12/2016.
 *
 * @author asiafrank
 */
class Main {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.apply();

        ActorRef crunch = system.actorOf(Crunch.props(), "crunch");
        ActorRef stopActor = system.actorOf(StopActor.props(crunch), "graceful-stop");
        ActorRef manager = system.actorOf(Manager.props(crunch), "manager");

        manager.tell("job", ActorRef.noSender());
        manager.tell(Manager.SHUTDOWN, ActorRef.noSender());
        stopActor.tell("stop", ActorRef.noSender());
        manager.tell("job", ActorRef.noSender());
    }
}
