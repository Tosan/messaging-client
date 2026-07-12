package com.tosan.client.messaging.chapar.api.exception;

/**
 * @author saber mortazavi
 * @since 11/27/2024
 */
public class ChaparMessagingValidationException extends ChaparMessagingRuntimeException {

    public ChaparMessagingValidationException() {
    }

    public ChaparMessagingValidationException(String message) {
        super(message);
    }

    public ChaparMessagingValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
