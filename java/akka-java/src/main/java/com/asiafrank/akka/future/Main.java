package com.asiafrank.akka.future;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * Main
 * <p>
 * Created by zhangxf on 12/6/2016.
 */
class Main {
    public static void main(String[] args){
        final ActorSystem system = ActorSystem.apply("future");
        final ActorRef thinking = system.actorOf(Thinking.props(), "thinking");
        final ActorRef conclusion = system.actorOf(Conclusion.props(), "conclusion");
        final ActorRef ask = system.actorOf(Ask.props(thinking, conclusion), "ask");

        ask.tell("start", ActorRef.noSender());
    }
}
