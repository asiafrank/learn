package com.asiafrank.akka.example1;

import akka.actor.UntypedActor;

/**
 * DemoMessagesActor - Recommended Practices
 * <p>
 * Created by zhangxf on 12/2/2016.
 */
class DemoMessagesActor extends UntypedActor {
    public static class Greeting {
        private final String from;

        public Greeting(String from) {
            this.from = from;
        }

        public String getGreeter() {
            return this.from;
        }
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Greeting) {
            getSender().tell("Hello " + ((Greeting) message).getGreeter(), self());
        } else {
            unhandled(message);
        }
    }
}
