package com.asiafrank.akka.example2;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * example
 *
 * Created by zhangxf on 12/1/2016.
 */
public class Main {
    public static void main(String[] args){
        final ActorSystem system = ActorSystem.apply();
        final ActorRef parent = system.actorOf(Parent.props());
        final ActorRef parent2 = system.actorOf(Parent.props());
        System.out.println(parent.path());
        System.out.println(parent2.path());
    }
}
