package com.asiafrank.akka.fsm;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import java.util.ArrayList;
import java.util.List;

/**
 * MyFSMBase
 * <p>
 * </p>
 * Created at 1/11/2017.
 *
 * @author zhangxf
 */
abstract class MyFSMBase extends UntypedActor {

    /**
     * This is the mutable state of this state machine.
     */
    protected enum State {
        IDLE, ACTIVE;
    }

    private State state = State.IDLE;

    private ActorRef target;

    private List<Object> queue;

    protected void init(ActorRef target) {
        this.target = target;
        this.queue = new ArrayList<>();
    }

    protected void setState(State s) {
        if (state != s) {
            transition(state, s);
            state = s;
        }
    }

    protected void enqueue(Object o) {
        if (queue != null) {
            queue.add(o);
        }
    }

    protected List<Object> drainQueue() {
        final List<Object> q = queue;
        if (q == null) {
            throw new IllegalStateException("drainQueue(): not yet initialized");
        }
        queue = new ArrayList<>();
        return q;
    }

    //=======================================
    // Here are the interrogation methods:
    //=======================================

    protected boolean isInitialized() {
        return target != null;
    }

    protected State getState() {
        return state;
    }

    protected ActorRef getTarget() {
        if (target == null) {
            throw new IllegalStateException("getTarget(): not yet initialized");
        }
        return target;
    }

    abstract protected void transition(State old, State next);
}
