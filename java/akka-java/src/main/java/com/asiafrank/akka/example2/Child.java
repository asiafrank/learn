package com.asiafrank.akka.example2;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;
import scala.Option;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

/**
 * Greeter
 *
 * Created by zhangxf on 11/25/2016.
 */
public class Child extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public Child() {
    }

    public static Props props() {
        return Props.create(new Creator<Actor>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Actor create() throws Exception {
                return new Child();
            }
        });
    }

    @Override
    public void preStart() throws Exception {

    }

    @Override
    public void onReceive(Object msg) throws Throwable {
        if (msg.equals("stop")) {
            getContext().stop(getSelf());
            return;
        }

        // Actor的消息处理都是单线程
        log.info("start-" + msg.toString());
        if (msg.equals("a")) {
            Thread.sleep(3000);
        } else {
            Thread.sleep(1000);
        }
        log.info("end-" + msg.toString());
    }

    @Override
    public void aroundReceive(PartialFunction<Object, BoxedUnit> receive, Object msg) {
        log.info("aroundReceive");
        super.aroundReceive(receive, msg);
    }

    @Override
    public void aroundPreStart() {
        log.info("aroundPreStart");
        super.aroundPreStart();
    }

    @Override
    public void aroundPostStop() {
        log.info("aroundPostStop");
        super.aroundPostStop();
    }

    @Override
    public void aroundPreRestart(Throwable reason, Option<Object> message) {
        log.info("aroundPreRestart");
        super.aroundPreRestart(reason, message);
    }

    @Override
    public void postStop() throws Exception {
        // clean up resources here ...
        log.info("postStop");
    }

    @Override
    public void aroundPostRestart(Throwable reason) {
        log.info("aroundPostRestart");
        super.aroundPostRestart(reason);
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        log.info("preRestart");
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        log.info("postRestart");
    }
}
