package com.asiafrank.akka.fault.example1;

import java.util.HashMap;
import java.util.Map;

/**
 * DummyDB
 * <p>
 * </p>
 * Created at 1/6/2017.
 *
 * @author zhangxf
 */
final class DummyDB {
    static final DummyDB instance = new DummyDB();
    private final Map<String, Long> db = new HashMap<String, Long>();

    private DummyDB() {
    }

    synchronized void save(String key, Long value) throws StorageException {
        if (11 <= value && value <= 14)
            throw new StorageException("Simulated store failure " + value);
        db.put(key, value);
    }

    synchronized Long load(String key) throws StorageException {
        return db.get(key);
    }
}
