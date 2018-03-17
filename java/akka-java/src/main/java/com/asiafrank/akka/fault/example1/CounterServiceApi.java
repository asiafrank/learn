package com.asiafrank.akka.fault.example1;

/**
 * CounterServiceApi
 * <p>
 * </p>
 * Created at 1/6/2017.
 *
 * @author zhangxf
 */
interface CounterServiceApi {
    Object GetCurrentCount = "GetCurrentCount";
    class CurrentCount {
        final String key;
        final long count;

        public CurrentCount(String key, long count) {
            this.key = key;
            this.count = count;
        }

        public String toString() {
            return String.format("%s(%s, %s)", getClass().getSimpleName(), key, count);
        }
    }
}
