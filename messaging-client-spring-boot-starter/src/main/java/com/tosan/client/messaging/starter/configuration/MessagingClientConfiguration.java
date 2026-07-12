package com.tosan.client.messaging.starter.configuration;

import com.tosan.client.http.core.Constants;
import com.tosan.client.http.core.HttpClientProperties;
import com.tosan.client.messaging.starter.config.MessagingClientConfig;
import com.tosan.tools.mask.starter.business.enumeration.MaskType;
import com.tosan.tools.mask.starter.config.SecureParameter;
import com.tosan.tools.mask.starter.config.SecureParametersConfig;
import com.tosan.tools.mask.starter.configuration.MaskBeanConfiguration;
import com.tosan.tools.mask.starter.replace.JacksonReplaceHelper;
import com.tosan.tools.mask.starter.replace.JsonReplaceHelperDecider;
import com.tosan.tools.mask.starter.replace.RegexReplaceHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;

/**
 * @author Ali Alimohammadi
 * @since 01/13/2026
 */
@Configuration
public class MessagingClientConfiguration {

    @Bean("messaging-client-clientConfig")
    @ConfigurationProperties(prefix = "messaging-client")
    @ConditionalOnMissingBean(name = "messaging-client-clientConfig")
    public HttpClientProperties clientConfig() {
        return new MessagingClientConfig();
    }

    @Bean("messaging-client-secured-parameters")
    @ConditionalOnMissingBean(name = "messaging-client-secured-parameters")
    public SecureParametersConfig secureParametersConfig() {
        HashSet<SecureParameter> securedParameters = new HashSet<>(MaskBeanConfiguration.SECURED_PARAMETERS);
        securedParameters.add(Constants.AUTHORIZATION_SECURE_PARAM);
        securedParameters.add(Constants.PROXY_AUTHORIZATION_SECURE_PARAM);
        securedParameters.add(new SecureParameter("message", MaskType.COMPLETE));
        return new SecureParametersConfig(securedParameters);
    }

    @Bean("messaging-client-replace-helper")
    public JsonReplaceHelperDecider replaceHelperDecider(
            JacksonReplaceHelper jacksonReplaceHelper,
            RegexReplaceHelper regexReplaceHelper,
            @Qualifier("messaging-client-secured-parameters") SecureParametersConfig secureParametersConfig) {
        return new JsonReplaceHelperDecider(jacksonReplaceHelper, regexReplaceHelper, secureParametersConfig);
    }
}
