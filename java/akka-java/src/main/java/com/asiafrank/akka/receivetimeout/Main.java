package com.asiafrank.akka.receivetimeout;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * Main
 * <p>
 * Created by zhangxf on 12/7/2016.
 */
class Main {
    public static void main(String[] args){
        final ActorSystem system = ActorSystem.apply();
        final ActorRef receiveTimeout = system.actorOf(MyReceiveTimeout.props());
        receiveTimeout.tell("Hello", ActorRef.noSender());
    }
}
