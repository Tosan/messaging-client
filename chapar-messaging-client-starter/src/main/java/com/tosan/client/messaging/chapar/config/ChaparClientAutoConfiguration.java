package com.tosan.client.messaging.chapar.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tosan.client.http.core.HttpClientProperties;
import com.tosan.client.http.core.factory.ConfigurableApacheHttpClientFactory;
import com.tosan.client.http.resttemplate.starter.configuration.AbstractHttpClientConfiguration;
import com.tosan.client.http.resttemplate.starter.impl.ExternalServiceInvoker;
import com.tosan.client.http.resttemplate.starter.util.HttpLoggingInterceptorUtil;
import com.tosan.client.messaging.chapar.api.ChaparMessagingService;
import com.tosan.client.messaging.chapar.config.properties.ChaparClientProperties;
import com.tosan.client.messaging.chapar.service.ChaparMessagingServiceImpl;
import com.tosan.client.messaging.chapar.service.ChaparTokenCacheService;
import com.tosan.client.messaging.chapar.service.ChaparTokenManager;
import com.tosan.client.messaging.chapar.service.assembler.ChaparAssembler;
import com.tosan.client.redis.api.TedissonCacheManager;
import io.micrometer.observation.ObservationRegistry;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Amirhossein Zamanzade
 * @since 5/16/26
 */
@AutoConfiguration
public class ChaparClientAutoConfiguration extends AbstractHttpClientConfiguration {

    private final ChaparClientProperties chaparClientProperties;

    public ChaparClientAutoConfiguration(
            @Qualifier("chapar-client-properties") ChaparClientProperties chaparClientProperties) {
        this.chaparClientProperties = chaparClientProperties;
    }

    @Override
    public String getExternalServiceName() {
        return "chapar-service";
    }

    @Override
    public HttpClientProperties clientConfig() {
        return chaparClientProperties;
    }

    @Bean("chapar-apacheHttpClientFactory")
    @Override
    public ConfigurableApacheHttpClientFactory apacheHttpClientFactory(
            @Qualifier("chapar-apacheHttpClientBuilder") HttpClientBuilder builder,
            @Qualifier("chapar-connectionManagerFactory") PoolingHttpClientConnectionManagerBuilder connectionManagerBuilder,
            @Qualifier("chapar-client-properties") HttpClientProperties httpClientProperties) {
        return new ConfigurableApacheHttpClientFactory(builder, connectionManagerBuilder, httpClientProperties);
    }

    @Bean("chapar-clientHttpRequestFactory")
    @Override
    public ClientHttpRequestFactory clientHttpRequestFactory(
            @Qualifier("chapar-apacheHttpClientFactory") ConfigurableApacheHttpClientFactory apacheHttpClientFactory) {
        return super.clientHttpRequestFactory(apacheHttpClientFactory);
    }

    @Bean("chapar-apacheHttpClientBuilder")
    @Override
    public HttpClientBuilder apacheHttpClientBuilder() {
        return super.apacheHttpClientBuilder();
    }

    @Bean("chapar-connectionManagerFactory")
    @Override
    public PoolingHttpClientConnectionManagerBuilder connectionManagerBuilder() {
        return PoolingHttpClientConnectionManagerBuilder.create();
    }

    @Bean("chapar-objectMapper")
    @Override
    public ObjectMapper objectMapper() {
        return super.objectMapper();
    }

    @Bean("chapar-httpMessageConverter")
    @Override
    public HttpMessageConverter<Object> httpMessageConverter(
            @Qualifier("chapar-objectMapper") ObjectMapper objectMapper) {
        return super.httpMessageConverter(objectMapper);
    }

    @Bean("chapar-responseErrorHandler")
    @Override
    public ResponseErrorHandler responseErrorHandler() {
        return new DefaultResponseErrorHandler();
    }

    @Bean("chapar-httpLoggingRequestInterceptor")
    @Override
    public ClientHttpRequestInterceptor httpLoggingRequestInterceptor(
            HttpLoggingInterceptorUtil httpLoggingInterceptorUtil) {
        return super.httpLoggingRequestInterceptor(httpLoggingInterceptorUtil);
    }

    @Bean("chapar-restTemplateBuilder")
    @Override
    public RestTemplateBuilder restTemplateBuilder(
            @Qualifier("chapar-httpMessageConverter") HttpMessageConverter<Object> httpMessageConverter,
            @Qualifier("chapar-clientHttpRequestFactory") ClientHttpRequestFactory clientHttpRequestFactory,
            @Qualifier("chapar-httpLoggingRequestInterceptor") List<ClientHttpRequestInterceptor> clientHttpRequestInterceptors,
            @Qualifier("chapar-responseErrorHandler") ResponseErrorHandler responseErrorHandler) {
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        converters.add(new FormHttpMessageConverter());
        converters.add(httpMessageConverter);
        return new RestTemplateBuilder()
                .messageConverters(converters)
                .requestFactory(() -> clientHttpRequestFactory)
                .additionalInterceptors(clientHttpRequestInterceptors)
                .errorHandler(responseErrorHandler);
    }

    @Bean("chapar-restTemplate")
    @Override
    public RestTemplate restTemplate(
            @Qualifier("chapar-restTemplateBuilder") RestTemplateBuilder builder,
            ObservationRegistry observationRegistry) {
        return super.restTemplate(builder, observationRegistry);
    }

    @Bean("chapar-serviceInvoker")
    public ExternalServiceInvoker serviceInvoker(
            @Qualifier("chapar-restTemplate") RestTemplate restTemplate,
            @Qualifier("chapar-client-properties") HttpClientProperties httpClientProperties) {
        return new ExternalServiceInvoker(restTemplate, httpClientProperties);
    }

    @Bean("chapar-tokenCacheService")
    public ChaparTokenCacheService chaparTokenCacheService(TedissonCacheManager tedissonCacheManager) {
        return new ChaparTokenCacheService(tedissonCacheManager);
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
}
