package com.tosan.client.messaging.starter.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tosan.client.http.core.HttpClientProperties;
import com.tosan.client.http.core.factory.ConfigurableApacheHttpClientFactory;
import com.tosan.client.http.starter.configuration.AbstractFeignConfiguration;
import com.tosan.client.http.starter.impl.feign.CustomErrorDecoder;
import com.tosan.client.http.starter.impl.feign.CustomErrorDecoderConfig;
import com.tosan.client.http.starter.impl.feign.ExceptionExtractType;
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
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author saber mortazavi
 * @since 11/30/2024
 */
@Configuration
@EnableFeignClients
@ConditionalOnProperty(name = "messaging-client.provider", havingValue = "ARMAGHAN_NEGAR")
public class ArmaghanNegarClientConfiguration extends AbstractFeignConfiguration {

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    @Override
    public String getExternalServiceName() {
        return "armaghan-negar-service";
    }

    @Override
    @Bean({"armaghan-negar-objectMapper"})
    public ObjectMapper objectMapper() {
        DateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().build();
        objectMapper
                .findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(dateFormat)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper;
    }

    @Override
    @Bean("armaghan-negar-httpFeignClientLogger")
    public Logger httpFeignClientLogger(
            @Qualifier("messaging-client-replace-helper") JsonReplaceHelperDecider replaceHelperDecider) {
        return super.httpFeignClientLogger(replaceHelperDecider);
    }

    @Override
    @Bean("armaghan-negar-apacheHttpClientFactory")
    public ConfigurableApacheHttpClientFactory apacheHttpClientFactory(
            @Qualifier("armaghan-negar-httpClientBuilder") HttpClientBuilder builder,
            @Qualifier("armaghan-negar-connectionManagerFactory") PoolingHttpClientConnectionManagerBuilder connectionManagerBuilder,
            @Qualifier("messaging-client-clientConfig") HttpClientProperties armaghanConfig) {
        return super.apacheHttpClientFactory(builder, connectionManagerBuilder, armaghanConfig);
    }

    @Override
    @Bean("armaghan-negar-clientHttpRequestFactory")
    public ClientHttpRequestFactory clientHttpRequestFactory(
            @Qualifier("armaghan-negar-apacheHttpClientFactory") ConfigurableApacheHttpClientFactory apacheHttpClientFactory) {
        return super.clientHttpRequestFactory(apacheHttpClientFactory);
    }

    @Override
    @Bean("armaghan-negar-httpclient")
    public CloseableHttpClient httpClient(
            @Qualifier("armaghan-negar-apacheHttpClientFactory") ConfigurableApacheHttpClientFactory apacheHttpClientFactory) {
        return super.httpClient(apacheHttpClientFactory);
    }

    @Override
    @Bean("armaghan-negar-connectionManagerFactory")
    public PoolingHttpClientConnectionManagerBuilder connectionManagerBuilder() {
        return super.connectionManagerBuilder();
    }

    @Override
    @Bean("armaghan-negar-feignClient")
    public Client feignClient(
            @Qualifier("armaghan-negar-httpclient") org.apache.hc.client5.http.classic.HttpClient httpClient) {
        return super.feignClient(httpClient);
    }

    @Override
    @Bean("armaghan-negar-requestInterceptor")
    public RequestInterceptor requestInterceptor() {
        return super.requestInterceptor();
    }

    @Override
    @Bean("armaghan-negar-requestInterceptors")
    public List<RequestInterceptor> requestInterceptors(
            @Qualifier("messaging-client-clientConfig") HttpClientProperties customServerClientConfig,
            @Qualifier("armaghan-negar-requestInterceptor") RequestInterceptor requestInterceptor) {
        return super.requestInterceptors(customServerClientConfig, requestInterceptor);
    }

    @Override
    @Bean("armaghan-negar-feignContract")
    public Contract feignContractWithCustomSpringConversion(
            @Qualifier("armaghan-negar-feignConversionService") ConversionService feignConversionService,
            List<AnnotatedParameterProcessor> processors) {
        return super.feignContractWithCustomSpringConversion(feignConversionService, processors);
    }

    @Override
    @Bean("armaghan-negar-feignConversionService")
    public FormattingConversionService feignConversionService(List<FeignFormatterRegistrar> feignFormatterRegistrars) {
        return super.feignConversionService(feignFormatterRegistrars);
    }

    @Override
    @Bean("armaghan-negar-feignEncoder")
    public Encoder feignEncoder(
            @Qualifier("armaghan-negar-jacksonHttpMessageConverter") HttpMessageConverter<Object> httpMessageConverter) {
        return super.feignEncoder(httpMessageConverter);
    }

    @Override
    @Bean("armaghan-negar-feignDecoder")
    public Decoder feignDecoder(
            @Qualifier("armaghan-negar-jacksonHttpMessageConverter") HttpMessageConverter<Object> httpMessageConverter) {
        return super.feignDecoder(httpMessageConverter);
    }

    @Override
    @Bean("armaghan-negar-jacksonHttpMessageConverter")
    public HttpMessageConverter<Object> httpMessageConverter(
            @Qualifier("armaghan-negar-objectMapper") ObjectMapper objectMapper) {
        return super.httpMessageConverter(objectMapper);
    }

    @Override
    @Bean("armaghan-negar-feignErrorDecoderConfig")
    public CustomErrorDecoderConfig customErrorDecoderConfig(
            @Qualifier("armaghan-negar-objectMapper") ObjectMapper objectMapper) {
        CustomErrorDecoderConfig customErrorDecoderConfig = new CustomErrorDecoderConfig();
        customErrorDecoderConfig.getScanPackageList().add("com.tosan.client.messaging.starter.provider.exception");
        customErrorDecoderConfig.setExceptionExtractType(ExceptionExtractType.EXCEPTION_IDENTIFIER_FIELDS);
        customErrorDecoderConfig.setCheckedExceptionClass(TosanWebServiceException.class);
        customErrorDecoderConfig.setUncheckedExceptionClass(TosanWebServiceRuntimeException.class);
        customErrorDecoderConfig.setObjectMapper(objectMapper);
        return customErrorDecoderConfig;
    }

    @Override
    @Bean("armaghan-negar-feignErrorDecoder")
    public CustomErrorDecoder customErrorDecoder(
            @Qualifier("armaghan-negar-feignErrorDecoderConfig") CustomErrorDecoderConfig customErrorDecoderConfig) {
        return new CustomErrorDecoder(customErrorDecoderConfig);
    }

    @Override
    @Bean("armaghan-negar-httpClientBuilder")
    public HttpClientBuilder apacheHttpClientBuilder() {
        return super.apacheHttpClientBuilder();
    }

    @Override
    @Bean("armaghan-negar-retryer")
    @ConditionalOnMissingBean(name = {"armaghan-negar-retryer"})
    public Retryer retryer() {
        return super.retryer();
    }

    @Override
    @Bean("armaghan-negar-feignLoggerLevel")
    @ConditionalOnMissingBean(name = {"armaghan-negar-feignLoggerLevel"})
    public Logger.Level feignLoggerLevel() {
        return super.feignLoggerLevel();
    }

    @Override
    @Bean("armaghan-negar-feignOption")
    public Request.Options options(
            @Qualifier("messaging-client-clientConfig") HttpClientProperties customServerClientConfig) {
        return super.options(customServerClientConfig);
    }

    @Bean("armaghan-negar-feignUndeclaredExceptionAspect")
    public ArmaghanNegarUndeclaredThrowableExceptionAspect feignUndeclaredThrowableExceptionAspect() {
        return new ArmaghanNegarUndeclaredThrowableExceptionAspect();
    }

    @Override
    @Bean("armaghan-negar-feignCapabilities")
    public List<Capability> feignCapabilities(ObservationRegistry observationRegistry) {
        return super.feignCapabilities(observationRegistry);
    }

    @Override
    @Bean("armaghan-negar-feignBuilder")
    public Feign.Builder feignBuilder(
            @Qualifier("armaghan-negar-feignClient") Client feignClient,
            @Qualifier("armaghan-negar-feignOption") Request.Options options,
            @Qualifier("armaghan-negar-requestInterceptor") List<RequestInterceptor> requestInterceptors,
            @Qualifier("armaghan-negar-feignContract") Contract feignContract,
            @Qualifier("armaghan-negar-feignDecoder") Decoder feignDecoder,
            @Qualifier("armaghan-negar-feignEncoder") Encoder feignEncoder,
            @Qualifier("armaghan-negar-retryer") Retryer retryer,
            @Qualifier("armaghan-negar-feignLoggerLevel") Logger.Level logLevel,
            @Qualifier("armaghan-negar-feignErrorDecoder") CustomErrorDecoder errorDecoder,
            @Qualifier("armaghan-negar-httpFeignClientLogger") Logger logger,
            @Qualifier("armaghan-negar-feignCapabilities") List<Capability> feignCapabilities) {
        return super.feignBuilder(feignClient, options, requestInterceptors, feignContract, feignDecoder, feignEncoder,
                retryer, logLevel, errorDecoder, logger, feignCapabilities);
    }

    @Bean("armaghan-negar-service-client")
    public WebserviceApi armaghanNegarServiceClient(
            @Qualifier("messaging-client-clientConfig") HttpClientProperties glClientConfig,
            @Qualifier("armaghan-negar-feignBuilder") Feign.Builder feignBuilder) {
        return super.getFeignController(glClientConfig.getBaseServiceUrl(), feignBuilder, WebserviceApi.class);
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
            WebserviceApi webserviceApi,
            ArmaghanNegarAssembler armaghanNegarAssembler,
            ArmaghanNegarResponseHandler armaghanNegarResponseHandler) {
        return new ArmaghanNegarMessagingService(webserviceApi, armaghanNegarAssembler, armaghanNegarResponseHandler);
    }
}
