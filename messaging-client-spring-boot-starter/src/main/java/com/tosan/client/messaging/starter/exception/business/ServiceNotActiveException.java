package com.tosan.client.messaging.starter.exception.business;

import com.tosan.client.messaging.starter.exception.MessagingException;

/**
 * @author saber mortazavi
 * @since 11/27/2024
 */
public class ServiceNotActiveException extends MessagingException {

    public ServiceNotActiveException() {
    }

    public ServiceNotActiveException(String message, String code) {
        super(message, code);
    }
}
