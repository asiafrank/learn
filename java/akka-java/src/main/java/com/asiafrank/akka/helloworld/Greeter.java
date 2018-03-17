package com.asiafrank.akka.helloworld;

import akka.actor.UntypedActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Option;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

/**
 * Greeter
 *
 * Created by zhangxf on 11/25/2016.
 */
class Greeter extends UntypedActor {
    private static final Logger logger = LoggerFactory.getLogger(Greeter.class);

    enum Msg {
        GREET, DONE;
    }

    @Override
    public void preStart() throws Exception {
        logger.info("start");
        logger.info("path: " + getSelf().path());
        logger.info("address: " + getSelf().path().address());
    }

    @Override
    public void onReceive(Object msg) throws Throwable {
        if (msg == Msg.GREET) {
            System.out.println("Hello World!");
            getSender().tell(Msg.DONE, getSelf());
            // throw new NullPointerException("for test");
            getContext().stop(getSelf());
        } else {
            unhandled(msg);
        }
    }

    @Override
    public void aroundReceive(PartialFunction<Object, BoxedUnit> receive, Object msg) {
        logger.info("aroundReceive");
        super.aroundReceive(receive, msg);
    }

    @Override
    public void aroundPreStart() {
        logger.info("aroundPreStart");
        super.aroundPreStart();
    }

    @Override
    public void aroundPostStop() {
        logger.info("aroundPostStop");
        super.aroundPostStop();
    }

    @Override
    public void aroundPreRestart(Throwable reason, Option<Object> message) {
        logger.info("aroundPreRestart");
        super.aroundPreRestart(reason, message);
    }

    @Override
    public void postStop() throws Exception {
        // clean up resources here ...
        logger.info("postStop");
    }

    @Override
    public void aroundPostRestart(Throwable reason) {
        logger.info("aroundPostRestart");
        super.aroundPostRestart(reason);
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        logger.info("preRestart");
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        logger.info("postRestart");
        preStart();
    }
}
