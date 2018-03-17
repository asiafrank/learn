package com.asiafrank.akka.fault.example1;

/**
 * StorageApi
 * <p>
 * </p>
 * Created at 1/6/2017.
 *
 * @author zhangxf
 */
interface StorageApi {
    class Store {
        final Entry entry;

        Store(Entry entry) {
            this.entry = entry;
        }

        public String toString() {
            return String.format("%s(%s)", getClass().getSimpleName(), entry);
        }
    }

    class Entry {
        final String key;
        final long value;

        Entry(String key, long value) {
            this.key = key;
            this.value = value;
        }

        public String toString() {
            return String.format("%s(%s, %s)", getClass().getSimpleName(), key, value);
        }
    }

    class Get {
        final String key;

        Get(String key) {
            this.key = key;
        }

        public String toString() {
            return String.format("%s(%s)", getClass().getSimpleName(), key);
        }
    }
}
