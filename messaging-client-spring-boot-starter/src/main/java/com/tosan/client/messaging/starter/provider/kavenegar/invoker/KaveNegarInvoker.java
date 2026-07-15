package com.tosan.client.messaging.starter.provider.kavenegar.invoker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tosan.client.http.core.HttpClientProperties;
import com.tosan.client.http.restclient.starter.impl.ClientService;
import com.tosan.client.http.restclient.starter.impl.ExternalServiceInvoker;
import com.tosan.client.messaging.starter.config.MessagingClientConfig;
import lombok.Getter;

import java.util.Map;

/**
 * @author Ali Alimohammadi
 * @since 01/13/2026
 */
public class KaveNegarInvoker extends ExternalServiceInvoker {
    private final ObjectMapper objectMapper;
    @Getter
    private final MessagingClientConfig messagingClientConfig;

    public KaveNegarInvoker(ClientService clientService, MessagingClientConfig httpClientProperties,
                            ObjectMapper objectMapper) {
        super(clientService, httpClientProperties);
        this.objectMapper = objectMapper;
        this.messagingClientConfig = httpClientProperties;
    }

    public String generateUrl(String path) {
        String baseUrl = messagingClientConfig.getBaseServiceUrl();
        if (path == null || path.isBlank()) {
            return baseUrl;
        }
        String normalizedBase = baseUrl.endsWith("/") ? baseUrl : String.format("%s/", baseUrl);
        String normalizedPath = path.startsWith("/") ? path : String.format("/%s", path);
        return String.format("%s%s%s", normalizedBase, messagingClientConfig.getKaveNegar().getApiKey(), normalizedPath);
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
