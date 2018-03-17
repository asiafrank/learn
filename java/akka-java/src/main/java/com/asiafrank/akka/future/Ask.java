package com.asiafrank.akka.future;

import akka.actor.*;
import akka.dispatch.Futures;
import akka.dispatch.Mapper;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;


/**
 * Ask
 * <p>
 * Created by zhangxf on 12/6/2016.
 */
class Ask extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private final ActorRef thinking;
    private final ActorRef conclusion;
    private final Timeout t;

    private ActorSystem system;

    private Ask(ActorRef tell, ActorRef conclusion) {
        this.thinking = tell;
        this.conclusion = conclusion;
        this.t = new Timeout(Duration.create(5, TimeUnit.SECONDS));
    }

    public static Props props(final ActorRef tell, final ActorRef conclusion) {
        return Props.create(new Creator<Actor>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Actor create() throws Exception {
                return new Ask(tell, conclusion);
            }
        });
    }

    @Override
    public void preStart() throws Exception {
        this.system = getContext().system();
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (!(message instanceof String)
                || !message.equals("start")) {
            log.info("no start");
            return;
        }
        log.info(message.toString());

        final ArrayList<Future<Object>> futures = new ArrayList<>();
        futures.add(Patterns.ask(thinking, "request", 1000)); // using 1000ms timeout
        futures.add(Patterns.ask(thinking, "another request", t)); // using timeout from above

        final Future<Iterable<Object>> aggregate = Futures.sequence(futures,
                system.dispatcher());

        // this 'map' not the map of 'map reduce'
        final Future<Result> transformed = aggregate.map(new Mapper<Iterable<Object>, Result>() {
            @Override
            public Result apply(Iterable<Object> coll) {
                final Iterator<Object> it = coll.iterator();
                final String x = (String) it.next();
                final String s = (String) it.next();
                return new Result(x, s);
            }
        }, system.dispatcher());

        Patterns.pipe(transformed, system.dispatcher()).to(conclusion);
    }

    private class Result {
        String x;
        String s;

        public Result(String x, String s) {
            this.x = x;
            this.s = s;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "x='" + x + '\'' +
                    ", s='" + s + '\'' +
                    '}';
        }
    }
}
