package com.asiafrank.akka.fault.example1;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Saves key/value pairs to persistent storage when receiving Store message.
 * Replies with current value when receiving Get message. Will throw
 * StorageException if the underlying data store is out of order.
 */
final class Storage extends UntypedActor {
	final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	final DummyDB db = DummyDB.instance;

	@Override
	public void onReceive(Object message) {
		log.debug("received message {}", message);
		if (message instanceof StorageApi.Store) {
			StorageApi.Store store = (StorageApi.Store) message;
			db.save(store.entry.key, store.entry.value);
		} else if (message instanceof StorageApi.Get) {
			StorageApi.Get get = (StorageApi.Get) message;
			Long value = db.load(get.key);
			getSender().tell(new StorageApi.Entry(get.key, value == null ?
					Long.valueOf(0L) : value), getSelf());
		} else {
			unhandled(message);
		}
	}
}