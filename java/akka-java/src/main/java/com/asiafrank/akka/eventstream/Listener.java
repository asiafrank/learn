package com.asiafrank.akka.eventstream;

import akka.actor.UntypedActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener
 *
 * Created by zhangxf on 12/1/2016.
 */
class Listener extends UntypedActor {
    private static final Logger logger = LoggerFactory.getLogger(Listener.class);

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Jazz) {
            logger.info("{} is listening to: {}", self().path().name(), message);
        } else if (message instanceof Electronic) {
            logger.info("{} is listening to: {}", self().path().name(), message);
        }
    }
}
