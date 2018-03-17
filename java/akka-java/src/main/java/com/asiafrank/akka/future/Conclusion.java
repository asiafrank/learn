package com.asiafrank.akka.future;

import akka.actor.Actor;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;

/**
 * Conclusion
 * <p>
 * Created by zhangxf on 12/6/2016.
 */
class Conclusion extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props() {
        return Props.create(new Creator<Actor>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Actor create() throws Exception {
                return new Conclusion();
            }
        });
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        log.info(message.toString());
    }
}
