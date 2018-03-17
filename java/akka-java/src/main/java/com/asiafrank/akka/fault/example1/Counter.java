package com.asiafrank.akka.fault.example1;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Counter
 * <p>
 * The in memory count variable that will send current value to the Storage,
 * if there is any storage available at the moment.
 * </p>
 * Created at 1/6/2017.
 *
 * @author zhangxf
 */
final class Counter extends UntypedActor {
    final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    final String key;
    long count;
    ActorRef storage;

    public Counter(String key, long initialValue) {
        this.key = key;
        this.count = initialValue;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        log.debug("received message {}", message);
        if (message instanceof CounterApi.UseStorage) {
            storage = ((CounterApi.UseStorage) message).storage;
            storeCount();
        } else if (message instanceof Increment) {
            count += ((Increment) message).n;
            storeCount();
        } else if (message.equals(CounterServiceApi.GetCurrentCount)) {
            getSender().tell(new CounterServiceApi.CurrentCount(key, count), getSelf());
        } else {
            unhandled(message);
        }
    }

    void storeCount() {
        // Delegate dangerous work, to protect our valuable state.
        // We can continue without storage.
        if (storage != null) {
            storage.tell(new StorageApi.Store(new StorageApi.Entry(key, count)), getSelf());
        }
    }
}
