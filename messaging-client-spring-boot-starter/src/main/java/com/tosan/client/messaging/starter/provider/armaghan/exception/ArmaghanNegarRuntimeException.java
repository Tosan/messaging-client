package com.tosan.client.messaging.starter.provider.armaghan.exception;

import com.tosan.client.http.starter.impl.feign.exception.TosanWebServiceRuntimeException;

/**
 * @author saber mortazavi
 * @since 11/27/2024
 */
public class ArmaghanNegarRuntimeException extends TosanWebServiceRuntimeException {
    private String errorCode;

    public ArmaghanNegarRuntimeException() {
    }

    public ArmaghanNegarRuntimeException(String message) {
        super(message);
    }

    public ArmaghanNegarRuntimeException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ArmaghanNegarRuntimeException(String message, Throwable cause) {
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
