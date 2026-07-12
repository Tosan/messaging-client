package com.tosan.client.messaging.chapar.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tosan.client.http.resttemplate.starter.impl.ExternalServiceInvoker;
import com.tosan.client.messaging.chapar.api.ChaparMessagingService;
import com.tosan.client.messaging.chapar.api.dto.SendEventResponseDto;
import com.tosan.client.messaging.chapar.api.exception.ChaparMessagingRuntimeException;
import com.tosan.client.messaging.chapar.api.exception.ChaparMessagingValidationException;
import com.tosan.client.messaging.chapar.service.assembler.ChaparAssembler;
import com.tosan.client.messaging.chapar.service.enumeration.ChaparUrl;
import com.tosan.client.messaging.chapar.service.model.ErrorObject;
import com.tosan.client.messaging.chapar.service.model.SendEventRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import java.util.List;

/**
 * @author Amirhossein Zamanzade
 * @since 5/16/26
 */
@Slf4j
@RequiredArgsConstructor
public class ChaparMessagingServiceImpl implements ChaparMessagingService {

    private static final String CHAPAR_SEND_EVENT_LOG_PREFIX = "Chapar send event call";
    private final ExternalServiceInvoker externalServiceInvoker;
    private final ChaparTokenManager tokenManager;
    private final ObjectMapper objectMapper;
    private final ChaparAssembler chaparAssembler;

    @Override
    public SendEventResponseDto sendEvent(com.tosan.client.messaging.chapar.api.dto.SendEventRequestDto requestDto) {
        validateRequest(requestDto);
        return doSendEvent(requestDto, false);
    }

    private SendEventResponseDto doSendEvent(com.tosan.client.messaging.chapar.api.dto.SendEventRequestDto request,
            boolean retryRequest) {

        try {
            return invokeChaparSendEvent(request, retryRequest);
        } catch (HttpStatusCodeException e) {
            if (!retryRequest && isUnauthorized(e)) {
                log.info("{} returned {}; refreshing token and retrying once", CHAPAR_SEND_EVENT_LOG_PREFIX,
                        e.getStatusCode());
                tokenManager.invalidateToken();
                return doSendEvent(request, true);
            }
            throw mapStatusException(e);
        } catch (RestClientException e) {
            String message = CHAPAR_SEND_EVENT_LOG_PREFIX + " failed due to client error";
            log.error(message, e);
            throw new ChaparMessagingValidationException(message, e);
        }
    }

    private SendEventResponseDto invokeChaparSendEvent(
            com.tosan.client.messaging.chapar.api.dto.SendEventRequestDto request, boolean retryRequest) {

        HttpEntity<SendEventRequestDto> httpEntity =
                new HttpEntity<>(chaparAssembler.toSendEventRequest(request), buildHeaders());

        log.info("Calling Chapar send event endpoint{}", retryRequest ? " after token refresh" : "");

        ResponseEntity<SendEventResponseDto> response = externalServiceInvoker
                .getRestTemplate()
                .postForEntity(externalServiceInvoker.generateUrl(ChaparUrl.SEND_EVENT.getUrl()), httpEntity,
                        SendEventResponseDto.class);
        return response.getBody();
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenManager.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    private RuntimeException mapStatusException(HttpStatusCodeException e) {
        ErrorObject errorObject = readError(e);

        String fallbackMessage = "Chapar call failed";
        String message = (errorObject != null && StringUtils.hasText(errorObject.getMessage()))
                ? errorObject.getMessage()
                : fallbackMessage;

        String fallbackCode = String.valueOf(e.getStatusCode().value());
        String code = (errorObject != null && StringUtils.hasText(errorObject.getErrorCode()))
                ? errorObject.getErrorCode()
                : fallbackCode;

        String combinedMessage = String.format("%s (code: %s, status: %s)",
                message, code, e.getStatusCode().value());

        log.error("{} failed with status {} and body {}",
                CHAPAR_SEND_EVENT_LOG_PREFIX, e.getStatusCode(), e.getResponseBodyAsString());

        int statusValue = e.getStatusCode().value();

        if (statusValue == HttpStatus.UNAUTHORIZED.value()) {
            return new ChaparMessagingRuntimeException(combinedMessage, e);
        }

        if (statusValue == HttpStatus.BAD_REQUEST.value()) {
            return new ChaparMessagingValidationException(combinedMessage, e);
        }

        return new ChaparMessagingRuntimeException(combinedMessage, e);
    }

    private ErrorObject readError(HttpStatusCodeException e) {
        String responseBody = e.getResponseBodyAsString();
        if (StringUtils.hasText(responseBody)) {
            try {
                return objectMapper.readValue(responseBody, ErrorObject.class);
            } catch (Exception parseException) {
                log.warn("Failed to parse Chapar error response: {}", responseBody, parseException);
            }
        }

        ErrorObject errorObject = new ErrorObject();
        errorObject.setMessage(StringUtils.hasText(e.getMessage()) ? e.getMessage() : "Unknown error from Chapar");
        errorObject.setErrorCode(String.valueOf(e.getStatusCode().value()));
        return errorObject;
    }

    private boolean isUnauthorized(HttpStatusCodeException e) {
        return HttpStatus.UNAUTHORIZED.value() == e.getStatusCode().value();
    }

    private void validateRequest(com.tosan.client.messaging.chapar.api.dto.SendEventRequestDto request) {
        if (request == null) {
            throw new ChaparMessagingValidationException("SendEventRequestDto must not be null");
        }

        if (!StringUtils.hasText(request.getTemplateId())) {
            throw new ChaparMessagingValidationException("TemplateId is required");
        }

        if (!StringUtils.hasText(request.getDestination())) {
            throw new ChaparMessagingValidationException("Destination is required");
        }

        if (CollectionUtils.isEmpty(request.getParameters())) {
            throw new ChaparMessagingValidationException("Parameters must not be empty");
        }
    }
}
