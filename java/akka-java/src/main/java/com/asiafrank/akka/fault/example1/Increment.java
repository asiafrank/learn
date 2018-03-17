package com.asiafrank.akka.fault.example1;

/**
 * Increment
 * <p>
 * </p>
 * Created at 1/6/2017.
 *
 * @author zhangxf
 */
final class Increment {
    final long n;

    Increment(long n) {
        this.n = n;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), n);
    }
}
