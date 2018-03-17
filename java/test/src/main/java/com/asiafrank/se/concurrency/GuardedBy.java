package com.asiafrank.se.concurrency;

public @interface GuardedBy {
    String value() default "this";
}
