package com.tosan.client.messaging.starter.provider.kavenegar.invoker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tosan.client.http.core.HttpClientProperties;
import com.tosan.client.http.resttemplate.starter.impl.ExternalServiceInvoker;
import com.tosan.client.messaging.starter.config.MessagingClientConfig;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author Ali Alimohammadi
 * @since 01/13/2026
 */
public class KaveNegarInvoker extends ExternalServiceInvoker {
    private final ObjectMapper objectMapper;
    private final String apiKey;
    private final MessagingClientConfig messagingClientConfig;

    public KaveNegarInvoker(RestTemplate restTemplate, HttpClientProperties httpClientProperties,
                            ObjectMapper objectMapper) {
        super(restTemplate, httpClientProperties);
        this.objectMapper = objectMapper;
        this.messagingClientConfig = ((MessagingClientConfig) httpClientProperties);
        this.apiKey = ((MessagingClientConfig) httpClientProperties).getKaveNegar().getApiKey();
    }

    @Override
    public String generateUrl(String url) {
        return super.generateUrl("/" + apiKey + url);
    }

    public MessagingClientConfig getMessagingClientConfig() {
        return messagingClientConfig;
    }

    @SuppressWarnings("unchecked")
    public String createQueryParamString(Object request) {
        Map<String, String> dataMap = objectMapper.convertValue(request, Map.class);
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (String key : dataMap.keySet()) {
            if (first) first = false;
            else result.append("&");
            result.append(key);
            result.append("=");
            result.append(dataMap.get(key));
        }
        return result.toString();
    }
}
