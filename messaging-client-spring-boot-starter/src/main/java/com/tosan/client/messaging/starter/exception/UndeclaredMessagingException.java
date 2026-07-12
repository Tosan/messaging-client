package com.tosan.client.messaging.starter.exception;

import com.tosan.client.http.starter.impl.feign.exception.TosanWebServiceRuntimeException;

/**
 * @author saber mortazavi
 * @since 11/27/2024
 */
public class UndeclaredMessagingException extends TosanWebServiceRuntimeException {
    private String errorCode;

    public UndeclaredMessagingException() {
    }

    public UndeclaredMessagingException(String message) {
        super(message);
    }

    public UndeclaredMessagingException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public UndeclaredMessagingException(String message, Throwable cause) {
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
