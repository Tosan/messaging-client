package com.tosan.client.messaging.chapar.service;

import com.tosan.client.http.resttemplate.starter.impl.ExternalServiceInvoker;
import com.tosan.client.messaging.chapar.api.exception.ChaparMessagingRuntimeException;
import com.tosan.client.messaging.chapar.api.exception.ChaparMessagingValidationException;
import com.tosan.client.messaging.chapar.config.properties.ChaparClientProperties;
import com.tosan.client.messaging.chapar.service.enumeration.ChaparUrl;
import com.tosan.client.messaging.chapar.service.model.GetTokenResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import java.util.List;

/**
 * @author Amirhossein Zamanzade
 * @since 5/16/26
 */
@RequiredArgsConstructor
@Slf4j
public class ChaparTokenManager {

    private static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";
    private static final String FORM_GRANT_TYPE = "grant_type";
    private static final String FORM_CLIENT_SECRET = "client_secret";
    private static final String FORM_CLIENT_ID = "client_id";

    private static final long DEFAULT_EXPIRES_IN_SECONDS = 120L;

    private final ExternalServiceInvoker externalServiceInvoker;
    private final ChaparClientProperties chaparClientProperties;
    private final ChaparTokenCacheService tokenCache;

    public String getAccessToken() {
        String cached = tokenCache.getToken();
        if (StringUtils.hasText(cached)) {
            return cached;
        }

        synchronized (this) {
            cached = tokenCache.getToken();
            if (!StringUtils.hasText(cached)) {
                cached = fetchAndCacheToken();
            }
            return cached;
        }
    }

    public void invalidateToken() {
        tokenCache.removeToken();
    }

    private String fetchAndCacheToken() {
        validateConfig();

        String url = externalServiceInvoker.generateUrl(ChaparUrl.LOGIN.getUrl());
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(buildTokenForm(), buildTokenHeaders());

        try {
            ResponseEntity<GetTokenResponseDto> response =
                    externalServiceInvoker.getRestTemplate().postForEntity(url, entity, GetTokenResponseDto.class);

            GetTokenResponseDto body = response.getBody();
            String accessToken = (body != null) ? body.getAccessToken() : null;

            if (!StringUtils.hasText(accessToken)) {
                throw new ChaparMessagingRuntimeException("Chapar login response does not contain access_token");
            }

            long expiresIn = (body.getExpiresIn() != null) ? body.getExpiresIn() : DEFAULT_EXPIRES_IN_SECONDS;
            tokenCache.addToken(accessToken, expiresIn);

            return accessToken;

        } catch (HttpStatusCodeException e) {
            log.error("Chapar login call failed with status {} and body {}",
                    e.getStatusCode(), e.getResponseBodyAsString(), e);
            throw new ChaparMessagingRuntimeException(
                    "Chapar login call failed (status: " + e.getStatusCode().value() + ")", e);

        } catch (RestClientException e) {
            log.error("Chapar login call failed due to client error", e);
            throw new ChaparMessagingRuntimeException("Chapar login call failed due to client error", e);
        }
    }

    private HttpHeaders buildTokenHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    private MultiValueMap<String, String> buildTokenForm() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add(FORM_CLIENT_ID, chaparClientProperties.getClientId());
        form.add(FORM_CLIENT_SECRET, chaparClientProperties.getClientSecret());
        form.add(FORM_GRANT_TYPE, GRANT_TYPE_CLIENT_CREDENTIALS);
        return form;
    }

    private void validateConfig() {
        if (chaparClientProperties == null) {
            throw new ChaparMessagingValidationException("messaging-client.chapar configuration is required");
        }
        if (!StringUtils.hasText(chaparClientProperties.getClientId())) {
            throw new ChaparMessagingValidationException("messaging-client.chapar.client-id is required");
        }
        if (!StringUtils.hasText(chaparClientProperties.getClientSecret())) {
            throw new ChaparMessagingValidationException("messaging-client.chapar.client-secret is required");
        }
    }
}
