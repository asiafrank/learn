package com.asiafrank.akka.typed;

import akka.dispatch.Futures;
import akka.japi.Option;
import scala.concurrent.Future;

public class SquarerImpl implements Squarer {

    private String name;

    public SquarerImpl() {
        this.name = "default";
    }

    SquarerImpl(String name) {
        this.name = name;
    }

    @Override
    public void squareDontCare(int i) {
        int sq = i * i; // Nobody cares :(
    }

    @Override
    public Future<Integer> square(int i) {
        return Futures.successful(i * i);
    }

    @Override
    public Option<Integer> squareNowPlease(int i) {
        return Option.some(i * i);
    }

    @Override
    public int squareNow(int i) {
        return i * i;
    }
}