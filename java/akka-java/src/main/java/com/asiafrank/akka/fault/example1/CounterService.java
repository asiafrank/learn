package com.asiafrank.akka.fault.example1;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.List;

import static akka.actor.SupervisorStrategy.escalate;
import static akka.actor.SupervisorStrategy.restart;

/**
 * CounterService
 * <p>
 * Adds the value received in Increment message to a persistent counter.
 * Replies with CurrentCount when it is asked for CurrentCount. CounterService
 * supervise Storage and Counter.
 * </p>
 * Created at 1/6/2017.
 *
 * @author zhangxf
 */
final class CounterService extends UntypedActor {
    // Reconnect message
    final Object Reconnect = "Reconnect";

    private class SenderMsgPair {
        final ActorRef sender;
        final Object message;
        SenderMsgPair(ActorRef sender, Object message) {
            this.message = message;
            this.sender = sender;
        }
    }

    final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    final String key = getSelf().path().name();
    ActorRef storage;
    ActorRef counter;
    final List<SenderMsgPair> backlog = new ArrayList<SenderMsgPair>();
    final int MAX_BACKLOG = 10000;

    // Restart the storage child when StorageException is thrown.
    // After 3 restarts within 5 seconds it will be stopped.
    private static SupervisorStrategy strategy = new OneForOneStrategy(3,
            Duration.create("5 seconds"), new Function<Throwable, SupervisorStrategy.Directive>() {
        @Override
        public SupervisorStrategy.Directive apply(Throwable t) {
            if (t instanceof StorageException) {
                return restart();
            } else {
                return escalate();
            }
        }
    });

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public void preStart() {
        initStorage();
    }

    /**
     * The child storage is restarted in case of failure, but after 3 restarts,
     * and still failing it will be stopped. Better to back-off than
     * continuously failing. When it has been stopped we will schedule a
     * Reconnect after a delay. Watch the child so we receive Terminated message
     * when it has been terminated.
     */
    void initStorage() {
        storage = getContext().watch(getContext().actorOf(
                Props.create(Storage.class), "storage"));
        // Tell the counter, if any, to use the new storage
        if (counter != null)
            counter.tell(new CounterApi.UseStorage(storage), getSelf());
        // We need the initial value to be able to operate
        storage.tell(new StorageApi.Get(key), getSelf());
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        log.debug("received message {}", message);
        if (message instanceof StorageApi.Entry
                && ((StorageApi.Entry) message).key.equals(key)
                && counter == null) {
            // Reply from Storage of the initial value, now we can create the Counter
            final long value = ((StorageApi.Entry) message).value;
            counter = getContext().actorOf(Props.create(Counter.class, key, value));
            // Tell the counter to use current storage
            counter.tell(new CounterApi.UseStorage(storage), getSelf());
            // and send the buffered backlog to the counter
            for (SenderMsgPair each : backlog) {
                counter.tell(each.message, each.sender);
            }
            backlog.clear();
        } else if (message instanceof Increment) {
            forwardOrPlaceInBacklog(message);
        } else if (message.equals(CounterServiceApi.GetCurrentCount)) {
            forwardOrPlaceInBacklog(message);
        } else if (message instanceof Terminated) {
            // After 3 restarts the storage child is stopped.
            // We receive Terminated because we watch the child, see initStorage.
            storage = null;
            // Tell the counter that there is no storage for the moment
            counter.tell(new CounterApi.UseStorage(null), getSelf());
            // Try to re-establish storage after while
            getContext().system().scheduler().scheduleOnce(
                    Duration.create(10, "seconds"), getSelf(), Reconnect,
                    getContext().dispatcher(), null);
        } else if (message.equals(Reconnect)) {
            // Re-establish storage after the scheduled delay
            initStorage();
        } else {
            unhandled(message);
        }
    }

    private void forwardOrPlaceInBacklog(Object msg) {
        // We need the initial value from storage before we can start delegate to
        // the counter. Before that we place the messages in a backlog, to be sent
        // to the counter when it is initialized.
        if (counter == null) {
            if (backlog.size() >= MAX_BACKLOG)
                throw new ServiceUnavailable("CounterService not available," +
                        " lack of initial value");
            backlog.add(new SenderMsgPair(getSender(), msg));
        } else {
            counter.forward(msg, getContext());
        }
    }
}
