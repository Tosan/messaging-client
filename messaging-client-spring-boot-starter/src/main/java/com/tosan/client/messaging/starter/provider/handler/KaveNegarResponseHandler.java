package com.tosan.client.messaging.starter.provider.handler;

import com.tosan.client.messaging.starter.exception.MessagingException;
import com.tosan.client.messaging.starter.exception.MessagingRuntimeException;
import com.tosan.client.messaging.starter.exception.business.*;
import com.tosan.client.messaging.starter.provider.kavenegar.model.KaveNegarResponseDto;
import com.tosan.client.messaging.starter.provider.kavenegar.model.ReturnInfo;

/**
 * @author Ali Alimohammadi
 * @since 1/13/26
 */
public class KaveNegarResponseHandler {

    public void handleError(KaveNegarResponseDto response) throws MessagingException {
        if (response == null || response.getReturnInfo() == null) {
            throw new MessagingRuntimeException("response is null", "0");
        }
        ReturnInfo returnInfo = response.getReturnInfo();
        String statusCode = returnInfo.getStatus();
        KaveNegarStatus kaveNegarStatus = KaveNegarStatus.findByValue(Integer.valueOf(statusCode));
        switch (kaveNegarStatus) {
            case BAD_API_KEY:
                throw new AuthenticationException(kaveNegarStatus.getMessage(), kaveNegarStatus.getCode().toString());
            case INVALID_SENDER:
                throw new InvalidOriginatorException(kaveNegarStatus.getMessage(), kaveNegarStatus.getCode().toString());
            case LOW_CREDIT:
                throw new LowCreditException(kaveNegarStatus.getMessage(), kaveNegarStatus.getCode().toString());
            case INVALID_CODE, INVALID_CHARACTER, INVALID_COUNT, INVALID_DATE, INVALID_METHOD, INVALID_ARRAY_LENGTH,
                 REQUIRED_PARAM:
                throw new MalformedRequestException(kaveNegarStatus.getMessage(), kaveNegarStatus.getCode().toString());
            case INVALID_RECEPTOR:
                throw new InvalidDestinationException(kaveNegarStatus.getMessage(), kaveNegarStatus.getCode().toString());
            case INVALID_IP, IP_LIMITED:
                throw new InvalidIpException(kaveNegarStatus.getMessage(), kaveNegarStatus.getCode().toString());
            case USER_BLOCKED, CALL_MESSAGE_NOT_REACHABLE, ADVANCED_SERVICE, CONFLICT:
                throw new ServiceNotActiveException(kaveNegarStatus.getMessage(), kaveNegarStatus.getCode().toString());
            case TEST_SERVICE:
                throw new MessagingServiceException(kaveNegarStatus.getMessage(), kaveNegarStatus.getCode().toString());
            default:
                throw new MessagingRuntimeException(kaveNegarStatus.getMessage(), kaveNegarStatus.getCode().toString());
        }
    }
}
