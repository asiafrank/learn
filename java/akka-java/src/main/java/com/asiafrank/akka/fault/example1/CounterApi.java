package com.asiafrank.akka.fault.example1;

import akka.actor.ActorRef;

/**
 * CounterApi
 * <p>
 * </p>
 * Created at 1/6/2017.
 *
 * @author zhangxf
 */
interface CounterApi {
    class UseStorage {
        final ActorRef storage;

        public UseStorage(ActorRef storage) {
            this.storage = storage;
        }
        public String toString() {
            return String.format("%s(%s)", getClass().getSimpleName(), storage);
        }
    }
}
