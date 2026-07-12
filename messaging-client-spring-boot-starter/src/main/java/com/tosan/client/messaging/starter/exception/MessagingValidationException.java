package com.tosan.client.messaging.starter.exception;

/**
 * @author saber mortazavi
 * @since 11/27/2024
 */
public class MessagingValidationException extends MessagingException {

    public MessagingValidationException() {
    }

    public MessagingValidationException(String message) {
        super(message);
    }

    public MessagingValidationException(String message, String errorCode) {
        super(message, errorCode);
    }
}
