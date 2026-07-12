package com.tosan.client.messaging.chapar.api.exception;

public class ChaparMessagingRuntimeException extends RuntimeException {
    public ChaparMessagingRuntimeException() {
    }

    public ChaparMessagingRuntimeException(String message) {
        super(message);
    }

    public ChaparMessagingRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
