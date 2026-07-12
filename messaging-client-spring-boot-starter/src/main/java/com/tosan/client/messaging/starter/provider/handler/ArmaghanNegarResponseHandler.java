package com.tosan.client.messaging.starter.provider.handler;

import com.tosan.client.messaging.starter.exception.MessagingException;
import com.tosan.client.messaging.starter.exception.MessagingRuntimeException;
import com.tosan.client.messaging.starter.exception.business.*;
import com.tosan.client.messaging.starter.provider.armaghan.modelsabstraction.ErrorModelHolder;

/**
 * @author saber mortazavi
 * @since 12/07/2024
 */
public class ArmaghanNegarResponseHandler {

    private void handleError(String errorCode) throws MessagingException {
        ArmaghanNegarStatus armaghanNegarStatus = ArmaghanNegarStatus.findByValue(errorCode);
        switch (armaghanNegarStatus) {
            case SUCCESS:
                return;
            case AUTHENTICATE_FAILED:
                throw new AuthenticationException(armaghanNegarStatus.getMessage(), armaghanNegarStatus.getCode());
            case INVALID_SENDER:
                throw new InvalidOriginatorException(armaghanNegarStatus.getMessage(), armaghanNegarStatus.getCode());
            case LOW_CREDIT:
                throw new LowCreditException(armaghanNegarStatus.getMessage(), armaghanNegarStatus.getCode());
            case BAD_REQUEST:
                throw new MalformedRequestException(armaghanNegarStatus.getMessage(), armaghanNegarStatus.getCode());
            case INVALID_RECEPTOR:
                throw new InvalidDestinationException(armaghanNegarStatus.getMessage(), armaghanNegarStatus.getCode());
            case INVALID_IP:
                throw new InvalidIpException(armaghanNegarStatus.getMessage(), armaghanNegarStatus.getCode());
            case SERVICE_IS_DISABLED:
                throw new ServiceNotActiveException(armaghanNegarStatus.getMessage(), armaghanNegarStatus.getCode());
            case INTERNAL_SERVER_ERROR:
                throw new MessagingServiceException(armaghanNegarStatus.getMessage(), armaghanNegarStatus.getCode());
            default:
                throw new MessagingRuntimeException("خطای ناشناخته رخ داده است. کد خطا: " + errorCode, errorCode);
        }
    }

    public void handleError(ErrorModelHolder response) throws MessagingException {
        handleError(response.getErrorModel().getErrorCode().toString());
    }

    public void oneToOneHandleError(String errorCode) throws MessagingException {
        handleError(errorCode);
    }
}
