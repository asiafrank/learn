package com.asiafrank.akka.fault.example1;

/**
 * StorageException
 * <p>
 * </p>
 * Created at 1/6/2017.
 *
 * @author zhangxf
 */
final class StorageException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public StorageException(String message) {
        super(message);
    }
}
