package com.asiafrank.akka.fault.example1;

import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.duration.Duration;

/**
 * Listener
 * <p>
 * Listens on progress from the worker and shuts down the system when enough
 * work has been done.
 * </p>
 * Created at 1/6/2017.
 *
 * @author zhangxf
 */
final class Listener extends UntypedActor {
    final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Throwable {
        log.debug("received message {}", message);
        if (message instanceof WorkerApi.Progress) {
            WorkerApi.Progress progress = (WorkerApi.Progress) message;
            log.info("Current progress: {} %", progress.percent);
            if (progress.percent >= 100.0) {
                log.info("That's all, shutting down");
                getContext().system().terminate();
            }
        } else if (message == ReceiveTimeout.getInstance()) {
            // No progress within 15 seconds, ServiceUnavailable
            log.error("Shutting down due to unavailable service");
            getContext().system().terminate();
        } else {
            unhandled(message);
        }
    }

    @Override
    public void preStart() throws Exception {
        // If we don't get any progress within 15 seconds then the service
        // is unavailable
        getContext().setReceiveTimeout(Duration.create("15 seconds"));
    }
}
