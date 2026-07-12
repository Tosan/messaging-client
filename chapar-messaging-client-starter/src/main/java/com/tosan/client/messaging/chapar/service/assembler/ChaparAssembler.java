package com.tosan.client.messaging.chapar.service.assembler;


import com.tosan.client.messaging.chapar.api.dto.SendEventResponseDto;
import com.tosan.client.messaging.chapar.service.model.SendEventRequestDto;
import com.tosan.client.messaging.chapar.service.model.enumeration.ChaparContactType;
import com.tosan.client.messaging.chapar.service.model.enumeration.ChaparSendType;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author Amirhossein Zamanzade
 * @since 5/16/26
 */
@RequiredArgsConstructor
public class ChaparAssembler {

    private static final String SMS_OVERRIDE = "sms";

    public SendEventRequestDto toSendEventRequest(
            com.tosan.client.messaging.chapar.api.dto.SendEventRequestDto request) {
        Map<String, Object> parameters = new HashMap<>();
        Map<String, Object> securedParameters = new HashMap<>();
        if (request != null && !CollectionUtils.isEmpty(request.getParameters())) {
            request.getParameters().forEach((key, value) -> {
                if (isSecuredParam(key, request.getSecuredParametersKeys())) {
                    securedParameters.put(key, value);
                } else {
                    parameters.put(key, value);
                }
            });
        }
        Map<String, Object> overrides = new HashMap<>();
        overrides.put(SMS_OVERRIDE, request.getDestination());
        return SendEventRequestDto.builder()
                .type(request.getTemplateId())
                .contactType(ChaparContactType.GENERIC)
                .parameters(parameters)
                .securedParameters(securedParameters)
                .overrides(overrides)
                .uuid(UUID.randomUUID().toString())
                .sendType(ChaparSendType.SYNC)
                .build();
    }

    public SendEventResponseDto toSendOtpResponse(com.tosan.client.messaging.chapar.api.dto.SendEventRequestDto request,
            com.tosan.client.messaging.chapar.service.model.SendEventResponseDto responseDto) {
        SendEventResponseDto sendEventResponseDto = new SendEventResponseDto();
        sendEventResponseDto.setUuid(responseDto.getUuid());
        return sendEventResponseDto;
    }

    private boolean isSecuredParam(String key, Set<String> securedParametersKeys) {
        if (CollectionUtils.isEmpty(securedParametersKeys)) {
            return false;
        }
        return securedParametersKeys.contains(key);
    }
}
