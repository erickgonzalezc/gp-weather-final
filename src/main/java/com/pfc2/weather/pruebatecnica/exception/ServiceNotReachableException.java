package com.pfc2.weather.pruebatecnica.exception;

public class ServiceNotReachableException extends RuntimeException {
    public ServiceNotReachableException(String reason) {
        super(reason);
    }
}
