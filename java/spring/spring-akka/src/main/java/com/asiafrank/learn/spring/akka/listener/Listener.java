package com.asiafrank.learn.spring.akka.listener;

import akka.actor.UntypedActor;

/**
 * Listener
 * <p>
 * </p>
 * Created at 12/12/2016.
 *
 * @author asiafrank
 */
public abstract class Listener extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Throwable {
        handle(message);
    }

    protected abstract void handle(Object o);
}
