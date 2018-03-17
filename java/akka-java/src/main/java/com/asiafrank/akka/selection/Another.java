package com.asiafrank.akka.selection;

import akka.actor.Actor;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;

/**
 * Another - path: /user/another
 * <p>
 * Created by zhangxf on 12/5/2016.
 */
class Another extends UntypedActor {
    final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props() {
        return Props.create(new Creator<Actor>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Actor create() throws Exception {
                return new Another();
            }
        });
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        // do something
    }
}
