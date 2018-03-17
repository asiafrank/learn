package com.asiafrank.akka.typed;

import akka.actor.ActorSystem;

/**
 * Main
 * <p>
 * </p>
 * Created at 1/9/2017.
 *
 * @author zhangxf
 */
final class Main {
    public static void main(String[] args){
        ActorSystem system = ActorSystem.apply();
        TypedActorDocTest test = new TypedActorDocTest(system, null);
        test.proxyAnyActorRef();
    }
}
