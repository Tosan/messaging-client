package com.tosan.client.messaging.chapar.config.properties;

import com.tosan.client.http.core.HttpClientProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Amirhossein Zamanzade
 * @since 5/16/26
 */
@Getter
@Setter
public class ChaparClientProperties extends HttpClientProperties {
    private String clientId;
    private String clientSecret;
}
