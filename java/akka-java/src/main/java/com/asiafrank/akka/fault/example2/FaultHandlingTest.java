package com.asiafrank.akka.fault.example2;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.testkit.JavaTestKit;
import akka.testkit.TestProbe;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.*;

/**
 * FaultHandlingTest
 * <p>
 * </p>
 * Created at 1/9/2017.
 *
 * @author zhangxf
 */
final class FaultHandlingTest {
    private static ActorSystem system;
    private static Duration timeout = Duration.create(5, TimeUnit.SECONDS);

    public static void main(String[] args) {
        try {
            mustEmploySupervisorStrategy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void mustEmploySupervisorStrategy() throws Exception {
        start();

        Props superProps = Props.create(Supervisor.class);
        ActorRef supervisor = system.actorOf(superProps, "supervisor");
        ActorRef child = (ActorRef) Await.result(ask(supervisor, Props.create(Child.class), 5000), timeout);

        // Resume Test
        child.tell(42, ActorRef.noSender());
        check(child);

        child.tell(new ArithmeticException(), ActorRef.noSender());
        check(child);

        child.tell(new NullPointerException(), ActorRef.noSender());
        check(child);

        final TestProbe probe = new TestProbe(system);
        probe.watch(child);
        child.tell(new IllegalArgumentException(), ActorRef.noSender());
        probe.expectMsgClass(Terminated.class);

        cleanup();
    }

    private static boolean check(ActorRef child) throws Exception {
        boolean rs = Await.result(ask(child, "get", 5000), timeout).equals(42);
        System.out.println(rs);
        return rs;
    }

    private static void start() {
        system = ActorSystem.create("FaultHandlingTest");
    }

    private static void cleanup() {
        JavaTestKit.shutdownActorSystem(system);
        system = null;
    }
}
