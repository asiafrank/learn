package com.asiafrank.akka.typed;

import akka.japi.Option;
import scala.concurrent.Future;

public interface Squarer {
    // fire-forget
    void squareDontCare(int i);

    // non-blocking send-request-reply
    Future<Integer> square(int i);

    // blocking send-request-reply
    Option<Integer> squareNowPlease(int i);

    // blocking send-request-reply
    int squareNow(int i);
}