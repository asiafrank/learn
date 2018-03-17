package com.asiafrank.akka.gracefulstop;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;
import akka.pattern.Patterns;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;


/**
 * GracefulStop
 * <p>
 * </p>
 * Created at 19/12/2016.
 *
 * @author asiafrank
 */
class StopActor extends UntypedActor {
    private ActorRef actorRef;

    @Override
    public void onReceive(Object message) {
        if (message.equals("stop")) {
            Future<Boolean> stopped = Patterns.gracefulStop(actorRef,
                Duration.create(5, TimeUnit.SECONDS), Manager.SHUTDOWN);

            try {
                Await.result(stopped, Duration.create(6, TimeUnit.SECONDS));
                // The actor has been stopped
                System.out.println("stopped: " + actorRef.path());
            } catch (Exception e) {
                // the actor wasn't stopped within 5 seconds
                e.printStackTrace();
            }
        } else {
            unhandled(message);
        }
    }

    private StopActor(ActorRef actorRef) {
        this.actorRef = actorRef;
    }

    public static Props props(final ActorRef actorRef) {
        return Props.create(new Creator<Actor>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Actor create() throws Exception {
                return new StopActor(actorRef);
            }
        });
    }
}
