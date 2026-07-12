package com.tosan.client.messaging.starter.exception.business;

import com.tosan.client.messaging.starter.exception.MessagingValidationException;

/**
 * @author saber mortazavi
 * @since 11/27/2024
 */
public class InvalidIpException extends MessagingValidationException {

    public InvalidIpException() {
    }

    public InvalidIpException(String message, String code) {
        super(message, code);
    }
}
