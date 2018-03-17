package com.asiafrank.akka.fault.example2;

import akka.actor.UntypedActor;

/**
 * Child
 * <p>
 * </p>
 * Created at 1/9/2017.
 *
 * @author zhangxf
 */
final class Child extends UntypedActor {
    int state = 0;

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Exception) {
            throw (Exception) message;
        } else if (message instanceof  Integer) {
            state = (Integer) message;
        } else if (message.equals("get")) {
            getSender().tell(state, getSelf());
        } else {
            unhandled(message);
        }
    }
}
