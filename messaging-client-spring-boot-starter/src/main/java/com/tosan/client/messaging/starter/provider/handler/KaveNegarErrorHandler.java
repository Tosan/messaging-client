package com.tosan.client.messaging.starter.provider.handler;

import com.tosan.client.http.resttemplate.starter.impl.interceptor.AbstractErrorHandler;
import com.tosan.client.messaging.starter.provider.kavenegar.model.KaveNegarResponseDto;
import com.tosan.client.messaging.starter.exception.MessagingRuntimeException;
import com.tosan.client.messaging.starter.exception.UndeclaredMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.HttpClientErrorException;

/**
 * @author Ali Alimohammadi
 * @since 01/13/2026
 */
@RequiredArgsConstructor
public class KaveNegarErrorHandler extends AbstractErrorHandler {

    private final KaveNegarResponseHandler kaveNegarResponseHandler;

    @Override
    public void mappingException(Exception exception) {
        if (exception instanceof HttpClientErrorException httpClientErrorException) {
            KaveNegarResponseDto errorResponse = httpClientErrorException.getResponseBodyAs(KaveNegarResponseDto.class);
            try {
                kaveNegarResponseHandler.handleError(errorResponse);
            } catch (Exception e) {
                throw new UndeclaredMessagingException(e.getMessage(), e);
            }
        }
        throw new MessagingRuntimeException(exception.getMessage(), exception);
    }
}
