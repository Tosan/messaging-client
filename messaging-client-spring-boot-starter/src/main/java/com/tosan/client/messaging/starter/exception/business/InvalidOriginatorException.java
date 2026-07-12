package com.tosan.client.messaging.starter.exception.business;

import com.tosan.client.messaging.starter.exception.MessagingValidationException;

/**
 * @author saber mortazavi
 * @since 11/27/2024
 */
public class InvalidOriginatorException extends MessagingValidationException {

    public InvalidOriginatorException() {
    }

    public InvalidOriginatorException(String message, String code) {
        super(message, code);
    }
}
