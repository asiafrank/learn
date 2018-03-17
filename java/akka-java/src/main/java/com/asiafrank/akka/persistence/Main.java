package com.asiafrank.akka.persistence;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.FSM;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Main
 * <p>
 * </p>
 * Created at 1/19/2017.
 *
 * @author zhangxf
 */
final class Main {
    public static void main(String[] args) {
        final Config config = ConfigFactory.load("persistent.conf");
        ActorSystem system = ActorSystem.apply("persistence", config);
        ActorRef persistentActor = system.actorOf(Props.create(ExamplePersistentActor.class));
        persistentActor.tell(new CMD("Example"), ActorRef.noSender());
        persistentActor.tell("snap", ActorRef.noSender());
        persistentActor.tell("print", ActorRef.noSender());

        ActorRef myPersistentActor = system.actorOf(Props.create(MyPersistentActor.class));
        myPersistentActor.tell("a", ActorRef.noSender());
        myPersistentActor.tell("b", ActorRef.noSender());
        myPersistentActor.tell("c", ActorRef.noSender());

        // Safe stop PersistentActors
        persistentActor.tell("stop", ActorRef.noSender());
        myPersistentActor.tell("stop", ActorRef.noSender());
    }
}
