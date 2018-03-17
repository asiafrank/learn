package com.asiafrank.akka.persistence;

import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;
import akka.persistence.SnapshotOffer;
import akka.persistence.UntypedPersistentActor;

import java.util.Arrays;

/**
 * ExamplePersistentActor
 * <p>
 * </p>
 * Created at 1/17/2017.
 *
 * @author zhangxf
 */
public final class ExamplePersistentActor extends UntypedPersistentActor {

    final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private ExampleState state = new ExampleState();

    @Override
    public String persistenceId() {
        return "sample-id-1";
    }

    @Override
    public void onReceiveRecover(Object msg) throws Throwable {
        System.out.println("onReceiveRecover invoked");
        if (msg instanceof Evt) {
            System.out.println("onReceiveRecover Evt");
            state.update((Evt) msg);
        } else if (msg instanceof SnapshotOffer) {
            System.out.println("onReceiveRecover SnapshotOffer");
            state = (ExampleState) ((SnapshotOffer) msg).snapshot();
        } else {
            unhandled(msg);
        }
    }

    @Override
    public void onReceiveCommand(Object msg) throws Throwable {
        System.out.println("onReceiveCommand invoked: " + msg.toString());

        if (msg.equals("stop")) {
            getContext().stop(getSelf());
            return;
        }

        if (msg instanceof CMD) {
            System.out.println("onReceiveCommand CMD");
            final String data = ((CMD) msg).getData();
            final Evt evt1 = new Evt(data + "-" + getNumEvents());
            final Evt evt2 = new Evt(data + "-" + getNumEvents() + 1);
            persistAll(Arrays.asList(evt1, evt2), new Procedure<Evt>() {
                @Override
                public void apply(Evt param) throws Exception {
                    System.out.println("persistAll apply");
                    state.update(param);
                    if (param.equals(evt2)) {
                        getContext().system().eventStream().publish(param);
                    }
                }
            });
        } else if (msg.equals("snap")) {
            // IMPORTANT: create a copy of snapshot
            // because ExampleState is mutable!!
            saveSnapshot(state.copy());
        } else if (msg.equals("print")) {
            System.out.println(state);
        } else {
            unhandled(msg);
        }
    }

    public int getNumEvents() {
        return state.size();
    }
}
