package com.tosan.client.messaging.starter.exception.business;

import com.tosan.client.messaging.starter.exception.MessagingValidationException;

/**
 * @author Ali Alimohammadi
 * @since 01/13/2026
 */
public class InvalidOtpTypeException extends MessagingValidationException {

    public InvalidOtpTypeException() {
    }

    public InvalidOtpTypeException(String message) {
        super(message);
    }

    public InvalidOtpTypeException(String message, String code) {
        super(message, code);
    }
}
