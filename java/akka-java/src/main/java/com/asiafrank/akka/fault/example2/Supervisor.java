package com.asiafrank.akka.fault.example2;

import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.*;

/**
 * Supervisor
 * <p>
 * </p>
 * Created at 1/9/2017.
 *
 * @author zhangxf
 */
final class Supervisor extends UntypedActor {
    private static SupervisorStrategy strategy = new OneForOneStrategy(10, Duration.create("1 minute"),
            t -> {
                if (t instanceof ArithmeticException) {
                    return resume();
                } else if (t instanceof NullPointerException) {
                    return restart();
                } else if (t instanceof IllegalArgumentException) {
                    return stop();
                } else {
                    return escalate();
                }
            });

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Props) {
            getSender().tell(getContext().actorOf((Props) message), getSelf());
        } else {
            unhandled(message);
        }
    }
}
