package com.tosan.client.messaging.starter.exception;

import com.tosan.client.http.starter.impl.feign.exception.TosanWebServiceRuntimeException;

/**
 * @author saber mortazavi
 * @since 11/27/2024
 */
public class MessagingRuntimeException extends TosanWebServiceRuntimeException {
    private String errorCode;

    public MessagingRuntimeException() {
    }

    public MessagingRuntimeException(String message) {
        super(message);
    }

    public MessagingRuntimeException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public MessagingRuntimeException(String message, Throwable cause) {
        super(message, cause);
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
