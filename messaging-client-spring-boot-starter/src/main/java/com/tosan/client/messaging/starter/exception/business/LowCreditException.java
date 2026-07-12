package com.tosan.client.messaging.starter.exception.business;

import com.tosan.client.messaging.starter.exception.MessagingException;

/**
 * @author saber mortazavi
 * @since 11/27/2024
 */
public class LowCreditException extends MessagingException {

    public LowCreditException() {
    }

    public LowCreditException(String message, String code) {
        super(message, code);
    }
}
