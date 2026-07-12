package com.tosan.client.messaging.starter.exception.business;

import com.tosan.client.messaging.starter.exception.MessagingRuntimeException;

/**
 * @author saber mortazavi
 * @since 11/27/2024
 */
public class MessagingServiceException extends MessagingRuntimeException {

    public MessagingServiceException() {
    }

    public MessagingServiceException(String message, String code) {
        super(message, code);
    }

    public MessagingServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
