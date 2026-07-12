package com.tosan.client.messaging.starter.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tosan.client.http.core.HttpClientProperties;
import com.tosan.client.http.core.factory.ConfigurableApacheHttpClientFactory;
import com.tosan.client.http.resttemplate.starter.configuration.AbstractHttpClientConfiguration;
import com.tosan.client.http.resttemplate.starter.util.HttpLoggingInterceptorUtil;
import com.tosan.client.messaging.starter.interceptor.KaveNegarUndeclaredExceptionAspect;
import com.tosan.client.messaging.starter.provider.handler.KaveNegarErrorHandler;
import com.tosan.client.messaging.starter.provider.handler.KaveNegarResponseHandler;
import com.tosan.client.messaging.starter.provider.kavenegar.invoker.KaveNegarInvoker;
import com.tosan.client.messaging.starter.service.MessagingService;
import com.tosan.client.messaging.starter.service.assembler.KaveNegarAssembler;
import com.tosan.client.messaging.starter.service.impl.KaveNegarMessagingService;
import com.tosan.tools.mask.starter.replace.JsonReplaceHelperDecider;
import io.micrometer.observation.ObservationRegistry;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Ali Alimohammadi
 * @since 01/13/2026
 */
@Configuration
@ConditionalOnProperty(name = "messaging-client.provider", havingValue = "KAVE_NEGAR")
public class KaveNegarClientConfiguration extends AbstractHttpClientConfiguration {

    public static final String DATE_TIME_PATTERN = "YYYY/MM/DD HH:MM:SS";

    @Override
    public String getExternalServiceName() {
        return "kave-negar-service";
    }

    @Bean("kave-negar-objectMapper")
    @Override
    public ObjectMapper objectMapper() {
        return super.objectMapper()
                .setDateFormat(new SimpleDateFormat(DATE_TIME_PATTERN))
                .registerModule(new JavaTimeModule());
    }

    @Override
    public HttpClientProperties clientConfig() {
        return null;
    }

    @Bean("kave-negar-apacheHttpClientFactory")
    @Override
    public ConfigurableApacheHttpClientFactory apacheHttpClientFactory(
            @Qualifier("kave-negar-apacheHttpClientBuilder") HttpClientBuilder builder,
            @Qualifier("kave-negar-connectionManagerFactory") PoolingHttpClientConnectionManagerBuilder connectionManagerBuilder,
            @Qualifier("messaging-client-clientConfig") HttpClientProperties httpClientProperties) {
        return new ConfigurableApacheHttpClientFactory(builder, connectionManagerBuilder, httpClientProperties);
    }

    @Bean("kave-negar-clientHttpRequestFactory")
    @Override
    public ClientHttpRequestFactory clientHttpRequestFactory(
            @Qualifier("kave-negar-apacheHttpClientFactory") ConfigurableApacheHttpClientFactory apacheHttpClientFactory) {
        return super.clientHttpRequestFactory(apacheHttpClientFactory);
    }

    @Bean("kave-negar-apacheHttpClientBuilder")
    @Override
    public HttpClientBuilder apacheHttpClientBuilder() {
        return super.apacheHttpClientBuilder();
    }

    @Bean("kave-negar-connectionManagerFactory")
    @Override
    public PoolingHttpClientConnectionManagerBuilder connectionManagerBuilder() {
        return PoolingHttpClientConnectionManagerBuilder.create();
    }

    @Bean("kave-negar-httpMessageConverter")
    @Override
    public HttpMessageConverter<Object> httpMessageConverter(
            @Qualifier("kave-negar-objectMapper") ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);
        converter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED));
        return converter;
    }

    @Bean("kave-negar-restTemplateBuilder")
    @Override
    public RestTemplateBuilder restTemplateBuilder(
            @Qualifier("kave-negar-httpMessageConverter") HttpMessageConverter<Object> httpMessageConverter,
            @Qualifier("kave-negar-clientHttpRequestFactory") ClientHttpRequestFactory clientHttpRequestFactory,
            @Qualifier("kave-negar-httpLoggingRequestInterceptor") List<ClientHttpRequestInterceptor> clientHttpRequestInterceptors,
            @Qualifier("kave-negar-responseErrorHandler") ResponseErrorHandler responseErrorHandler) {
        return super.restTemplateBuilder(
                httpMessageConverter, clientHttpRequestFactory, clientHttpRequestInterceptors, responseErrorHandler);
    }

    @Bean("kave-negar-httpLoggingInterceptorUtil")
    public HttpLoggingInterceptorUtil httpLoggingInterceptorUtil(
            @Qualifier("messaging-client-replace-helper") JsonReplaceHelperDecider replaceHelperDecider) {
        return new HttpLoggingInterceptorUtil(replaceHelperDecider);
    }

    @Bean("kave-negar-httpLoggingRequestInterceptor")
    @Override
    public ClientHttpRequestInterceptor httpLoggingRequestInterceptor(
            @Qualifier("kave-negar-httpLoggingInterceptorUtil") HttpLoggingInterceptorUtil httpLoggingInterceptorUtil) {
        return super.httpLoggingRequestInterceptor(httpLoggingInterceptorUtil);
    }

    @Bean("kave-negar-clientHttpRequestInterceptors")
    @Override
    public List<ClientHttpRequestInterceptor> clientHttpRequestInterceptors(
            @Qualifier("messaging-client-clientConfig") HttpClientProperties httpClientProperties,
            @Qualifier("kave-negar-httpLoggingRequestInterceptor") ClientHttpRequestInterceptor httpLoggingRequestInterceptor) {
        return super.clientHttpRequestInterceptors(httpClientProperties, httpLoggingRequestInterceptor);
    }

    @Bean("kave-negar-responseErrorHandler")
    @Override
    public ResponseErrorHandler responseErrorHandler() {
        return new KaveNegarErrorHandler(new KaveNegarResponseHandler());
    }

    @Bean("kave-negar-UndeclaredExceptionAspect")
    public KaveNegarUndeclaredExceptionAspect KaveNegarUndeclaredExceptionAspect() {
        return new KaveNegarUndeclaredExceptionAspect();
    }

    @Bean("kave-negar-restTemplate")
    @Override
    public RestTemplate restTemplate(
            @Qualifier("kave-negar-restTemplateBuilder") RestTemplateBuilder builder,
            ObservationRegistry observationRegistry) {
        return super.restTemplate(builder, observationRegistry);
    }

    @Bean("kave-negar-serviceInvoker")
    public KaveNegarInvoker serviceInvoker(
            @Qualifier("kave-negar-restTemplate") RestTemplate restTemplate,
            @Qualifier("messaging-client-clientConfig") HttpClientProperties httpClientProperties,
            @Qualifier("kave-negar-objectMapper") ObjectMapper objectMapper) {
        return new KaveNegarInvoker(restTemplate, httpClientProperties, objectMapper);
    }

    @Bean("kave-negar-assembler")
    public KaveNegarAssembler kaveNegarAssembler() {
        return new KaveNegarAssembler();
    }

    @Bean("messaging-service-client")
    public MessagingService kaveNegarMessagingService(@Qualifier("kave-negar-serviceInvoker")
                                                      KaveNegarInvoker serviceInvoker,
                                                      KaveNegarAssembler kaveNegarAssembler) {
        return new KaveNegarMessagingService(serviceInvoker, kaveNegarAssembler);
    }
}
