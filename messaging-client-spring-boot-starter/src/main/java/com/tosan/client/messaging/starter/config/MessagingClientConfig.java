package com.tosan.client.messaging.starter.config;

import com.tosan.client.http.core.HttpClientProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author Ali Alimohammadi
 * @since 01/13/2026
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessagingClientConfig extends HttpClientProperties {
    private MessagingProviderType provider;

    @NestedConfigurationProperty
    private KaveNegarClientConfig kaveNegar;

    @NestedConfigurationProperty
    private ArmaghanNegarClientConfig armaghanNegar;
}
