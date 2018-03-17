package com.asiafrank.akka.eventstream;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.DeadLetter;
import akka.actor.Props;

/**
 * Main
 *
 * Created by zhangxf on 12/1/2016.
 */
class Main {
    public static void main(String[] args){
        ActorSystem system = ActorSystem.apply();

        final ActorRef actor = system.actorOf(Props.create(DeadLetterActor.class));
        system.eventStream().subscribe(actor, DeadLetter.class);

        final ActorRef jazzListener = system.actorOf(Props.create(Listener.class));
        final ActorRef musicListener = system.actorOf(Props.create(Listener.class));
        system.eventStream().subscribe(jazzListener, Jazz.class);
        system.eventStream().subscribe(musicListener, AllKindsOfMusic.class);

        // only musicListener gets this message, since it listens to *all* kinds of music:
        system.eventStream().publish(new Electronic("Parov Stelar"));

        // jazzListener and musicListener will be notified about Jazz:
        system.eventStream().publish(new Jazz("Sonny Rollins"));

        system.terminate();
    }
}
