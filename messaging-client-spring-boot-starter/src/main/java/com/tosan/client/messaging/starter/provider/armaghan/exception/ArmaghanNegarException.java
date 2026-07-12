package com.tosan.client.messaging.starter.provider.armaghan.exception;

import com.tosan.client.http.starter.impl.feign.exception.TosanWebServiceException;

/**
 * @author saber mortazavi
 * @since 11/27/2024
 */
public class ArmaghanNegarException extends TosanWebServiceException {
    private String errorCode;

    public ArmaghanNegarException() {
    }

    public ArmaghanNegarException(String message) {
        super(message);
    }

    public ArmaghanNegarException(String message, String errorCode) {
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
