package com.asiafrank.akka.helloworld;

import akka.actor.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HelloWorld
 *
 * Created by zhangxf on 11/25/2016.
 */
class HelloWorld extends UntypedActor {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorld.class);

    @Override
    public void preStart() throws Exception {
        // create the greeter actor
        final ActorRef greeter = getContext().actorOf(Props.create(Greeter.class), "greeter");
        // tell it to perfom the greeting
        greeter.tell(Greeter.Msg.GREET, getSelf());
        getContext().watch(greeter); // watch the terminated event of greeter

        logger.info("path: " + getSelf().path());
        logger.info("address: " + getSelf().path().address());
    }

    @Override
    public void onReceive(Object msg) throws Throwable {
        if (msg instanceof Terminated) {
            Terminated t = (Terminated) msg;
            logger.info(t.actor().path() + " terminated");
            getContext().stop(getSelf());
            return;
        }

        if (msg == Greeter.Msg.DONE) {
            // when the greeter is done, stop this actor and with it the application
            logger.info(msg.toString());
        } else {
            unhandled(msg);
        }
    }
}
