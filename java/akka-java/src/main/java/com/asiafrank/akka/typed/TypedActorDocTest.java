package com.asiafrank.akka.typed;

import akka.actor.*;
import akka.dispatch.Futures;
import akka.japi.Creator;
import akka.japi.Option;
import akka.routing.RoundRobinGroup;
import scala.concurrent.Future;
import scala.util.Random;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * TypedActorDocTest
 * <p>
 * </p>
 * Created at 1/6/2017.
 *
 * @author zhangxf
 */
final class TypedActorDocTest {
    private ActorSystem system = null;
    private Object someReference = null;

    TypedActorDocTest(ActorSystem system, Object someReference) {
        this.system = system;
        this.someReference = someReference;
    }

    public void mustGetTheTypedActorExtension() {
        // Returns the Typed Actor Extension
        TypedActorExtension extension = TypedActor.get(system);

        // Returns whether the reference is a Typed Actor Proxy or not
        extension.isTypedActor(someReference);

        // Returns the backing Akka Actor behind an external Typed Actor Proxy
        extension.getActorRefFor(someReference);

        // Returns the current ActorContext,
        // method only valid within methods of a TypedActor implementation
        ActorContext context = TypedActor.context();

        // Returns the external proxy of the current Typed Actor,
        // method only valid within methods of a TypedActor implementation
        Squarer sq = TypedActor.self();

        // Returns a contextual instance of the Typed Actor Extension
        // this means that if you create other Typed Actors with this,
        // they will become children to the current Typed Actor.
        TypedActor.get(TypedActor.context());
    }

    public void createATypedActor() {
        Squarer mySquarer = TypedActor.get(system).typedActorOf(
                new TypedProps<>(Squarer.class, SquarerImpl.class));
        Squarer othreSquarer = TypedActor.get(system).typedActorOf(
                new TypedProps<>(Squarer.class,
                        (Creator<SquarerImpl>) () -> new SquarerImpl("foo")), "name");

        mySquarer.squareDontCare(10);

        Future<Integer> fSquare = mySquarer.square(10); // A Future[int]

        Option<Integer> oSquare = mySquarer.squareNowPlease(10); // Option[int]

        int iSquare = mySquarer.squareNow(10);

        assertEquals(100, oSquare.get().intValue());

        assertEquals(100, iSquare);

        TypedActor.get(system).stop(mySquarer);

        TypedActor.get(system).poisonPill(othreSquarer);
    }

    public void createHierarchies() {
        Squarer childSquarer = TypedActor.get(TypedActor.context()).typedActorOf(
                new TypedProps<SquarerImpl>(Squarer.class, SquarerImpl.class));

        // Use "childSquarer" as a Squarer
    }

    public void proxyAnyActorRef() {
        TypedActorExtension extension = TypedActor.get(system);
        Squarer s = extension.typedActorOf(new TypedProps<>(Squarer.class,
                (Creator<SquarerImpl>) () -> new SquarerImpl("foo")));
        System.out.println(extension.isTypedActor(s));

        ActorRef actorRefToRemoteActor = extension.getActorRefFor(s);
        Squarer typedActor = extension.typedActorOf(new TypedProps<>(Squarer.class), actorRefToRemoteActor);
        // Use "typedActor" as FooBar
        int x = typedActor.squareNow(2);
        System.out.println(x);
    }

    interface HasName {
        String name();
    }

    private class Named implements HasName {
        private int id = new Random().nextInt(1024);

        @Override
        public String name() {
            return "name" + id;
        }
    }

    public void typedRouterPattern() {
        TypedActorExtension typed = TypedActor.get(system);

        Named named1 = typed.typedActorOf(new TypedProps<>(Named.class));
        Named named2 = typed.typedActorOf(new TypedProps<>(Named.class));

        List<Named> routees = new ArrayList<>();
        routees.add(named1);
        routees.add(named2);

        List<String> routeePaths = new ArrayList<>();
        routeePaths.add(typed.getActorRefFor(named1).path().toStringWithoutAddress());
        routeePaths.add(typed.getActorRefFor(named2).path().toStringWithoutAddress());

        // prepare untyped router
        ActorRef router = system.actorOf(new RoundRobinGroup(routeePaths).props());

        // prepare typed proxy, forwarding MethodCall messages to `router`
        Named typedRouter = typed.typedActorOf(new TypedProps<>(Named.class), router);

        System.out.println("actor was: " + typedRouter.name());
        System.out.println("actor was: " + typedRouter.name());
        System.out.println("actor was: " + typedRouter.name());
        System.out.println("actor was: " + typedRouter.name());

        typed.poisonPill(named1);
        typed.poisonPill(named2);
        typed.poisonPill(typedRouter);
    }
}
