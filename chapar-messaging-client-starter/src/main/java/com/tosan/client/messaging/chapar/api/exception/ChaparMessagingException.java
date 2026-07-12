package com.tosan.client.messaging.chapar.api.exception;

public class ChaparMessagingException extends Exception {

    public ChaparMessagingException() {
    }

    public ChaparMessagingException(String message) {
        super(message);
    }

    public ChaparMessagingException(String message, Throwable cause) {
        super(message, cause);
    }
}
