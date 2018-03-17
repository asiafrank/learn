package com.asiafrank.akka.example2;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Parent
 *
 * Created by zhangxf on 11/25/2016.
 */
public class Parent extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private List<ActorRef> children;

    public Parent() {
        children = new LinkedList<>();
    }

    public static Props props() {
        return Props.create(new Creator<Actor>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Actor create() throws Exception {
                return new Parent();
            }
        });
    }

    @Override
    public void preStart() throws Exception {
        final ActorRef child1 = getContext().actorOf(Child.props());
        getContext().watch(child1);

        child1.tell("a", getSelf());
        child1.tell("b", getSelf());
        child1.tell("stop", getSelf());

        final ActorRef child2 = getContext().actorOf(Child.props());
        getContext().watch(child2);

        child2.tell("a", getSelf());
        child2.tell("b", getSelf());
        child2.tell("stop", getSelf());

        children.add(child1);
        children.add(child2);
    }

    @Override
    public void onReceive(Object msg) throws Throwable {
        if (msg instanceof Terminated) {
            Terminated t = (Terminated) msg;
            log.info(t.actor().path() + " terminated");
            children.remove(t.actor());
            if (children.isEmpty()) {
                log.info("{} try to terminate", getSelf().path());
                getContext().stop(getSelf());
            }
            return;
        }

        if (msg != null) {
            // TODO: do something
        } else {
            unhandled(msg);
        }
    }

    @Override
    public void postStop() throws Exception {
        // clean up resources here ...
        children.clear();
    }
}
