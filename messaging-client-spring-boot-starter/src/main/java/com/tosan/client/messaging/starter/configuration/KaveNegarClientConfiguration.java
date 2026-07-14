package com.tosan.client.messaging.starter.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tosan.client.http.restclient.starter.configuration.AbstractRestClientConfiguration;
import com.tosan.client.messaging.starter.config.MessagingClientConfig;
import com.tosan.client.messaging.starter.interceptor.KaveNegarUndeclaredExceptionAspect;
import com.tosan.client.messaging.starter.provider.handler.KaveNegarErrorHandler;
import com.tosan.client.messaging.starter.provider.handler.KaveNegarResponseHandler;
import com.tosan.client.messaging.starter.provider.kavenegar.invoker.KaveNegarInvoker;
import com.tosan.client.messaging.starter.service.MessagingService;
import com.tosan.client.messaging.starter.service.assembler.KaveNegarAssembler;
import com.tosan.client.messaging.starter.service.impl.KaveNegarMessagingService;
import com.tosan.tools.mask.starter.replace.JsonReplaceHelperDecider;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;

import java.text.SimpleDateFormat;

/**
 * @author Ali Alimohammadi
 * @since 01/13/2026
 */
@Configuration
@ConditionalOnProperty(name = "messaging-client.provider", havingValue = "KAVE_NEGAR")
public class KaveNegarClientConfiguration extends AbstractRestClientConfiguration<MessagingClientConfig> {

    private static final String DATE_TIME_PATTERN = "yyyy/MM/dd HH:mm:ss";
    private static final String SERVICE_NAME = "kave-negar-service";

    protected KaveNegarClientConfiguration(RestClient.Builder builder, ObservationRegistry observationRegistry,
                                           @Qualifier("messaging-client-replace-helper")
                                           JsonReplaceHelperDecider jacksonReplaceHelper) {
        super(SERVICE_NAME, MessagingClientConfig.class, builder, observationRegistry, jacksonReplaceHelper);
    }

    @Bean
    public KaveNegarInvoker serviceInvokerBean(MessagingClientConfig properties,
                                               @Qualifier("kave-negar-objectMapper")
                                               ObjectMapper objectMapper) {
        validateProperties(properties);
        return new KaveNegarInvoker(createClientService(properties), properties, objectMapper);
    }

    @Override
    protected ResponseErrorHandler createResponseErrorHandler() {
        return new KaveNegarErrorHandler(new KaveNegarResponseHandler());
    }

    @Bean("kave-negar-objectMapper")
    public ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .findModulesViaServiceLoader(true)
                .dateFormat(new SimpleDateFormat(DATE_TIME_PATTERN))
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .featuresToDisable(
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                        SerializationFeature.FAIL_ON_EMPTY_BEANS,
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
                )
                .build();
    }

    @Bean("kave-negar-UndeclaredExceptionAspect")
    public KaveNegarUndeclaredExceptionAspect KaveNegarUndeclaredExceptionAspect() {
        return new KaveNegarUndeclaredExceptionAspect();
    }

    @Bean
    public KaveNegarAssembler kaveNegarAssembler() {
        return new KaveNegarAssembler();
    }

    @Bean("messaging-service-client")
    public MessagingService kaveNegarMessagingService(KaveNegarInvoker serviceInvoker,
                                                      KaveNegarAssembler kaveNegarAssembler) {
        return new KaveNegarMessagingService(serviceInvoker, kaveNegarAssembler);
    }
}
