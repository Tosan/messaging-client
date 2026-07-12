package com.tosan.client.messaging.starter.exception.business;

import com.tosan.client.messaging.starter.exception.MessagingValidationException;

public class InvalidMediaTypeException extends MessagingValidationException {

    public InvalidMediaTypeException() {
    }

    public InvalidMediaTypeException(String message) {
        super(message);
    }

    public InvalidMediaTypeException(String message, String code) {
        super(message, code);
    }
}