package com.tosan.client.messaging.starter.exception.business;

import com.tosan.client.messaging.starter.exception.MessagingRuntimeException;

/**
 * @author saber mortazavi
 * @since 11/27/2024
 */
public class AuthenticationException extends MessagingRuntimeException {

    public AuthenticationException() {
    }

    public AuthenticationException(String message, String code) {
        super(message, code);
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
