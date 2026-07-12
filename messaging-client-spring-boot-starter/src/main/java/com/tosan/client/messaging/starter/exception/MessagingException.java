package com.tosan.client.messaging.starter.exception;

import com.tosan.client.http.starter.impl.feign.exception.TosanWebServiceException;

/**
 * @author saber mortazavi
 * @since 11/27/2024
 */
public class MessagingException extends TosanWebServiceException {
    private String errorCode;

    public MessagingException() {
    }

    public MessagingException(String message) {
        super(message);
    }

    public MessagingException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    @Override
    public String getErrorType() {
        return null;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }
}
