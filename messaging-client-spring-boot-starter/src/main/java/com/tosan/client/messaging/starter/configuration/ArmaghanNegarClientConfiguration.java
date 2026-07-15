package com.tosan.client.messaging.starter.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.tosan.client.http.core.HttpClientProperties;
import com.tosan.client.http.starter.configuration.AbstractFeignConfiguration;
import com.tosan.client.http.starter.impl.feign.CustomErrorDecoderConfig;
import com.tosan.client.http.starter.impl.feign.ExceptionExtractType;
import com.tosan.client.http.starter.impl.feign.ExternalServiceInvoker;
import com.tosan.client.http.starter.impl.feign.exception.TosanWebServiceException;
import com.tosan.client.http.starter.impl.feign.exception.TosanWebServiceRuntimeException;
import com.tosan.client.messaging.starter.config.MessagingClientConfig;
import com.tosan.client.messaging.starter.interceptor.ArmaghanNegarUndeclaredThrowableExceptionAspect;
import com.tosan.client.messaging.starter.provider.armaghan.facade.WebserviceApi;
import com.tosan.client.messaging.starter.provider.handler.ArmaghanNegarResponseHandler;
import com.tosan.client.messaging.starter.service.MessagingService;
import com.tosan.client.messaging.starter.service.assembler.ArmaghanNegarAssembler;
import com.tosan.client.messaging.starter.service.impl.ArmaghanNegarMessagingService;
import com.tosan.tools.mask.starter.replace.JsonReplaceHelperDecider;
import feign.*;
import feign.codec.Decoder;
import feign.codec.Encoder;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author saber mortazavi
 * @since 11/30/2024
 */
@Configuration
@EnableFeignClients
@ConditionalOnProperty(name = "messaging-client.provider", havingValue = "ARMAGHAN_NEGAR")
public class ArmaghanNegarClientConfiguration extends AbstractFeignConfiguration<MessagingClientConfig> {

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String SERVICE_NAME = "armaghan-negar-service";

    protected ArmaghanNegarClientConfiguration(ObservationRegistry observationRegistry,
                                               @Qualifier("messaging-client-replace-helper")
                                               JsonReplaceHelperDecider jacksonReplaceHelper,
                                               ObjectProvider<Feign.Builder> builderProvider,
                                               Encoder encoder,
                                               Decoder decoder,
                                               Contract contract) {
        super(SERVICE_NAME, MessagingClientConfig.class, observationRegistry, jacksonReplaceHelper,
                builderProvider, encoder, decoder, contract);
    }

    @Bean("armaghan-negar-objectMapper")
    public ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .findAndAddModules()
                .defaultDateFormat(new SimpleDateFormat(DATE_PATTERN))
                .defaultPropertyInclusion(JsonInclude.Value.construct(
                        JsonInclude.Include.NON_NULL,
                        JsonInclude.Include.ALWAYS)
                )
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .build();
    }

    @Override
    protected CustomErrorDecoderConfig createCustomErrorDecoderConfig(ObjectMapper objectMapper) {
        CustomErrorDecoderConfig customErrorDecoderConfig = new CustomErrorDecoderConfig();
        customErrorDecoderConfig.getScanPackageList().add("com.tosan.client.messaging.starter.provider.exception");
        customErrorDecoderConfig.setExceptionExtractType(ExceptionExtractType.EXCEPTION_IDENTIFIER_FIELDS);
        customErrorDecoderConfig.setCheckedExceptionClass(TosanWebServiceException.class);
        customErrorDecoderConfig.setUncheckedExceptionClass(TosanWebServiceRuntimeException.class);
        customErrorDecoderConfig.setObjectMapper(objectMapper);
        return customErrorDecoderConfig;
    }


    @Bean("armaghan-negar-feignUndeclaredExceptionAspect")
    public ArmaghanNegarUndeclaredThrowableExceptionAspect feignUndeclaredThrowableExceptionAspect() {
        return new ArmaghanNegarUndeclaredThrowableExceptionAspect();
    }

    @Bean("armaghan-negar-service-client")
    public ExternalServiceInvoker<WebserviceApi> armaghanNegarServiceClient(
            @Qualifier("messaging-client-clientConfig")
            MessagingClientConfig properties) {
        return super.createServiceInvoker(properties, properties.getBaseServiceUrl(), WebserviceApi.class);
    }


    @Bean("armaghan-negar-assembler")
    public ArmaghanNegarAssembler armaghanNegarAssembler(
            @Qualifier("messaging-client-clientConfig") HttpClientProperties messagingClientConfig) {
        return new ArmaghanNegarAssembler((MessagingClientConfig) messagingClientConfig);
    }

    @Bean("armaghan-negar-responseHandler")
    public ArmaghanNegarResponseHandler armaghanNegarResponseHandler() {
        return new ArmaghanNegarResponseHandler();
    }

    @Bean("messaging-service-client")
    public MessagingService armaghanNegarMessagingService(
            ExternalServiceInvoker<WebserviceApi> serviceInvoker,
            ArmaghanNegarAssembler armaghanNegarAssembler,
            ArmaghanNegarResponseHandler armaghanNegarResponseHandler) {
        return new ArmaghanNegarMessagingService(serviceInvoker, armaghanNegarAssembler, armaghanNegarResponseHandler);
    }
}
