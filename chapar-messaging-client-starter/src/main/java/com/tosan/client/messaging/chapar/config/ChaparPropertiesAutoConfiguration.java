package com.tosan.client.messaging.chapar.config;

import com.tosan.client.messaging.chapar.config.properties.ChaparClientProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author Amirhossein Zamanzade
 * @since 5/24/26
 */
@AutoConfiguration
public class ChaparPropertiesAutoConfiguration {

    @Bean("chapar-client-properties")
    @ConfigurationProperties("chapar.messaging-client")
    public ChaparClientProperties chaparClientProperties() {
        return new ChaparClientProperties();
    }
}
