package com.asiafrank.akka.eventstream;

import akka.actor.DeadLetter;
import akka.actor.UntypedActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DeadLetter
 *
 * Created by zhangxf on 12/1/2016.
 */
class DeadLetterActor extends UntypedActor {
    private static final Logger logger = LoggerFactory.getLogger(DeadLetterActor.class);

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof DeadLetter) {
            logger.info("msg: {}", message);
        }
    }
}
