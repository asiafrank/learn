package com.asiafrank.akka.selection;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * Main
 * <p>
 * Created by zhangxf on 12/5/2016.
 */
class Main {
    public static void main(String[] args){
        final ActorSystem system = ActorSystem.apply();
        ActorRef probe = system.actorOf(Probe.props(), "probe");
        ActorRef another = system.actorOf(Another.props(), "another");
        ActorRef follower = system.actorOf(Follower.props(probe), "follower");

        System.out.println(probe.path());
        System.out.println(another.path());
        System.out.println(follower.path());
    }
}
