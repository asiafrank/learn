package com.asiafrank.akka.example1;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;

/**
 * DemoActor - Recommended Practices
 * <p>
 * you can use like this:
 * <code>
 * ActorSystem system = ActorSystem.apply();
 * final ActorRef demo = system.actorOf(DemoActor.props(42), "demo");
 * </code>
 * <p>
 * Created by zhangxf on 12/2/2016.
 */
class DemoActor extends UntypedActor {
    final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    final int magicNumber;

    public DemoActor(int magicNumber) {
        this.magicNumber = magicNumber;
    }

    public static Props props(final int magicNumber) {
        return Props.create(new Creator<Actor>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Actor create() throws Exception {
                return new DemoActor(magicNumber);
            }
        });
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof String) {
            // some behavior here
            log.info("Received String message: {}", message);
        } else {
            unhandled(message);
        }
    }
}
