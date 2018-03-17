package com.asiafrank.akka.fsm;

import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * MyFSM
 * <p>
 * </p>
 * Created at 1/11/2017.
 *
 * @author zhangxf
 */
public final class MyFSM extends MyFSMBase {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private final Object flush = new Object();

    @Override
    public void onReceive(Object message) throws Throwable {
        if (getState() == State.IDLE) {
            if (message instanceof SetTarget)
                init(((SetTarget) message).ref);
            else whenUnhandled(message);
        } else if (getState() == State.ACTIVE) {
            if (message == flush) setState(State.IDLE);
            else whenUnhandled(message);
        }
    }

    @Override
    protected void transition(State old, State next) {
        if (old == State.ACTIVE) {
            getTarget().tell(new Batch(drainQueue()), getSender());
        }
    }

    private void whenUnhandled(Object message) {
        if (message instanceof Queue && isInitialized()) {
            enqueue(((Queue) message).o);
            setState(State.ACTIVE);
        } else {
            log.warning("received unknown message {} in state {}", message, getState());
        }
    }
}
