package com.asiafrank.akka.fault.example1;

/**
 * ServiceUnavailable
 * <p>
 * </p>
 * Created at 1/6/2017.
 *
 * @author zhangxf
 */
final class ServiceUnavailable extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ServiceUnavailable(String message) {
        super(message);
    }
}
