package com.asiafrank.akka.receivetimeout;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.duration.Duration;

/**
 * MyReceiveTimeout
 * <p>
 * Created by zhangxf on 12/6/2016.
 */
class MyReceiveTimeout extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private ActorRef child;

    public static Props props() {
        return Props.create(MyReceiveTimeout.class);
    }

    public MyReceiveTimeout() {
        getContext().setReceiveTimeout(Duration.create("30 seconds"));
        log.info("set receive timeout to 30 second");
    }

    @Override
    public void preStart() throws Exception {
        child = getContext().actorOf(Child.props());
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message.equals("Hello")) {
            child.tell("start", getSelf());
            // To set in response to a message
            getContext().setReceiveTimeout(Duration.create("1 second"));
            log.info("set receive timeout to 1 second");
        } else if (message instanceof ReceiveTimeout) {
            log.info("receive timeout");
            // To turn it off
            getContext().setReceiveTimeout(Duration.Undefined());
        } else {
            unhandled(message);
        }
    }
}
