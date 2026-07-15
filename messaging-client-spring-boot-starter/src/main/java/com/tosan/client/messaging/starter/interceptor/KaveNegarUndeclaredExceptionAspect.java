package com.tosan.client.messaging.starter.interceptor;

import com.tosan.client.http.restclient.starter.exception.HttpClientRequestExecuteException;
import com.tosan.client.messaging.starter.exception.MessagingException;
import com.tosan.client.messaging.starter.exception.MessagingRuntimeException;
import com.tosan.client.messaging.starter.exception.business.MessagingServiceException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author saber mortazavi
 * @since 11/30/2024
 */
@Aspect
public class KaveNegarUndeclaredExceptionAspect {

    @AfterThrowing(pointcut = "execution(* com.tosan.client.messaging.starter.service.impl.KaveNegarMessagingService.*(..))",
            throwing = "exception")
    public void processException(Exception exception) throws Throwable {
        Throwable cause = exception.getCause();
        if (cause instanceof MessagingException messagingException) {
            throw messagingException;
        } else if (cause instanceof MessagingRuntimeException messagingRuntimeException) {
            throw messagingRuntimeException;
        } else if (cause instanceof HttpClientRequestExecuteException httpClientRequestExecuteException) {
            throw httpClientRequestExecuteException;
        } else {
            throw new MessagingServiceException("Unknown exception occurred in kavenegar service", exception);
        }
    }
}
