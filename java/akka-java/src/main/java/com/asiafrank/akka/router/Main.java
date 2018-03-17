package com.asiafrank.akka.router;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.BalancingPool;
import akka.routing.FromConfig;
import akka.routing.RoundRobinGroup;
import akka.routing.RoundRobinPool;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Main
 * <p>
 * </p>
 * Created at 1/10/2017.
 *
 * @author zhangxf
 */
final class Main {
    public static void main(String[] args) {
        final Config config = ConfigFactory.load("application.conf");
        ActorSystem system = ActorSystem.apply("system", config.getConfig("router-config"));

        // sample example
        ActorRef master = system.actorOf(Props.create(Master.class), "master");

        tryTell(master);

        // router pool with config
        ActorRef router1 = system.actorOf(FromConfig.getInstance()
                .props(Worker.props()), "router1");

        tryTell(router1);

        // router pool programmatically
        ActorRef router2 = system.actorOf(new RoundRobinPool(5)
                .props(Worker.props()), "router2");

        tryTell(router2);

        // router group with config
        system.actorOf(Props.create(Workers1.class), "workers1");
        waiting(1000);

        ActorRef router3 = system.actorOf(FromConfig.getInstance()
                .props(), "router3");

        tryTell(router3);

        // router group programmatically
        system.actorOf(Props.create(Workers2.class), "workers2");
        waiting(1000);

        List<String> paths = Arrays.asList("/user/workers2/w5", "/user/workers2/w6", "/user/workers2/w7");
        ActorRef router4 = system.actorOf(new RoundRobinGroup(paths).props(), "router4");

        tryTell(router4);

        // balancing pool with config
        ActorRef router5 = system.actorOf(FromConfig.getInstance().props(
                        Props.create(Worker.class)), "router5");
        tryTell(router5);

        // balancing pool with programmatically
        ActorRef router6 = system.actorOf(new BalancingPool(5).props(
                        Props.create(Worker.class)), "router6");

        tryTell(router6);

        waiting(5000);

        system.terminate();
    }

    private static void waiting(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void tryTell(ActorRef receiver) {
        receiver.tell(new Work("a"), ActorRef.noSender());
        receiver.tell(new Work("b"), ActorRef.noSender());
        receiver.tell(new Work("c"), ActorRef.noSender());
        receiver.tell(new Work("d"), ActorRef.noSender());
        receiver.tell(new Work("e"), ActorRef.noSender());
        receiver.tell(new Work("f"), ActorRef.noSender());
        receiver.tell(new Work("g"), ActorRef.noSender());
    }
}
