package com.tosan.client.messaging.starter.exception.business;

import com.tosan.client.messaging.starter.exception.MessagingRuntimeException;

/**
 * @author saber mortazavi
 * @since 11/27/2024
 */
public class MalformedRequestException extends MessagingRuntimeException {

    public MalformedRequestException() {
    }

    public MalformedRequestException(String message, String code) {
        super(message, code);
    }
}
