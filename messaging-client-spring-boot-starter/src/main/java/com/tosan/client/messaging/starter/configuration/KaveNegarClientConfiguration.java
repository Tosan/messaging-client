package com.tosan.client.messaging.starter.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
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
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverters;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;

import java.text.SimpleDateFormat;
import java.util.List;

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

    @Override
    protected void configureMessageConverters(HttpMessageConverters.ClientBuilder converters) {
        super.configureMessageConverters(converters);
        JacksonJsonHttpMessageConverter jacksonConverter =
                new JacksonJsonHttpMessageConverter();
        jacksonConverter.setSupportedMediaTypes(List.of(
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_FORM_URLENCODED
        ));
        converters.addCustomConverter(jacksonConverter);
    }

    @Bean("kave-negar-objectMapper")
    public ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .findAndAddModules()
                .defaultDateFormat(new SimpleDateFormat(DATE_TIME_PATTERN))
                .defaultPropertyInclusion(JsonInclude.Value.construct(
                        JsonInclude.Include.NON_NULL,
                        JsonInclude.Include.ALWAYS)
                )
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
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
