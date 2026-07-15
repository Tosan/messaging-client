package com.tosan.client.messaging.chapar.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.tosan.client.http.restclient.starter.configuration.AbstractRestClientConfiguration;
import com.tosan.client.http.restclient.starter.impl.ExternalServiceInvoker;
import com.tosan.client.messaging.chapar.api.ChaparMessagingService;
import com.tosan.client.messaging.chapar.config.properties.ChaparClientProperties;
import com.tosan.client.messaging.chapar.service.ChaparMessagingServiceImpl;
import com.tosan.client.messaging.chapar.service.ChaparTokenCacheService;
import com.tosan.client.messaging.chapar.service.ChaparTokenManager;
import com.tosan.client.messaging.chapar.service.assembler.ChaparAssembler;
import com.tosan.tools.mask.starter.replace.JsonReplaceHelperDecider;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;

/**
 * @author Amirhossein Zamanzade
 * @since 5/16/26
 */
@AutoConfiguration
public class ChaparClientAutoConfiguration extends AbstractRestClientConfiguration<ChaparClientProperties> {

    private static final String SERVICE_NAME = "chapar-service";

    protected ChaparClientAutoConfiguration(RestClient.Builder builder, ObservationRegistry observationRegistry,
                                            @Qualifier("messaging-client-replace-helper")
                                            JsonReplaceHelperDecider jacksonReplaceHelper) {
        super(SERVICE_NAME, ChaparClientProperties.class, builder, observationRegistry, jacksonReplaceHelper);
    }

    @Bean({"chapar-objectMapper"})
    public ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .findAndAddModules()
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
    protected ResponseErrorHandler createResponseErrorHandler() {
        return new DefaultResponseErrorHandler();
    }

    @Bean("chapar-serviceInvoker")
    public ExternalServiceInvoker serviceInvoker(
            @Qualifier("chapar-client-properties") ChaparClientProperties httpClientProperties) {
        return super.createServiceInvoker(httpClientProperties);
    }

    @Bean("chapar-tokenManager")
    public ChaparTokenManager chaparTokenManager(
            @Qualifier("chapar-serviceInvoker") ExternalServiceInvoker externalServiceInvoker,
            @Qualifier("chapar-client-properties") ChaparClientProperties httpClientProperties,
            @Qualifier("chapar-tokenCacheService") ChaparTokenCacheService chaparTokenCacheService) {
        return new ChaparTokenManager(externalServiceInvoker, httpClientProperties, chaparTokenCacheService);
    }

    @Bean("chapar-assembler")
    public ChaparAssembler chaparAssembler() {
        return new ChaparAssembler();
    }

    @Bean("chapar-messaging-service-client")
    public ChaparMessagingService chaparMessagingService(
            @Qualifier("chapar-serviceInvoker") ExternalServiceInvoker externalServiceInvoker,
            @Qualifier("chapar-tokenManager") ChaparTokenManager tokenManager,
            @Qualifier("chapar-objectMapper") ObjectMapper objectMapper,
            @Qualifier("chapar-assembler") ChaparAssembler chaparAssembler) {
        return new ChaparMessagingServiceImpl(externalServiceInvoker, tokenManager, objectMapper, chaparAssembler);
    }

    @Bean("chapar-client-properties")
    @ConfigurationProperties("chapar.messaging-client")
    public ChaparClientProperties chaparClientProperties() {
        return new ChaparClientProperties();
    }
}
