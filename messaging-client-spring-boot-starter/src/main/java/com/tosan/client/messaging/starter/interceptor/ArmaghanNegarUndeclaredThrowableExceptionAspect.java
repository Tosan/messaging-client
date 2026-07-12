package com.tosan.client.messaging.starter.interceptor;

import com.tosan.client.http.starter.impl.feign.exception.FeignClientRequestExecuteException;
import com.tosan.client.http.starter.impl.feign.exception.UnknownException;
import com.tosan.client.messaging.starter.exception.MessagingRuntimeException;
import feign.FeignException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * @author saber mortazavi
 * @since 11/30/2024
 */
@Aspect
public class ArmaghanNegarUndeclaredThrowableExceptionAspect {

    @AfterThrowing(pointcut = "execution(* com.tosan.client.messaging.starter.provider.armaghan.facade.*.*(..))",
            throwing = "exception")
    public void processException(Exception exception) throws Throwable {
        if (exception instanceof UndeclaredThrowableException undeclaredThrowableException) {
            Throwable undeclaredThrowable = undeclaredThrowableException.getUndeclaredThrowable();
            if (undeclaredThrowable instanceof UnknownException unknownException) {
                throw new MessagingRuntimeException(unknownException.getJsonResponse(), unknownException);
            }
            throw new MessagingRuntimeException(undeclaredThrowable.getMessage(), undeclaredThrowable);
        } else if (exception instanceof FeignException) {
            throw new FeignClientRequestExecuteException(exception.getMessage(), exception);
        } else {
            throw exception;
        }
    }
}
