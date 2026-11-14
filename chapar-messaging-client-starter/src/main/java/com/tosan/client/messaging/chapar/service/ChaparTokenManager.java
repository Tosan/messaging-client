package com.tosan.client.messaging.chapar.service;

import com.tosan.client.http.restclient.starter.impl.ExternalServiceInvoker;
import com.tosan.client.messaging.chapar.api.exception.ChaparMessagingRuntimeException;
import com.tosan.client.messaging.chapar.api.exception.ChaparMessagingValidationException;
import com.tosan.client.messaging.chapar.config.properties.ChaparClientProperties;
import com.tosan.client.messaging.chapar.config.properties.ChaparGrantType;
import com.tosan.client.messaging.chapar.service.enumeration.ChaparUrl;
import com.tosan.client.messaging.chapar.service.model.GetTokenResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Amirhossein Zamanzade
 * @since 5/16/26
 */
@Slf4j
public class ChaparTokenManager {

    private static final String FORM_GRANT_TYPE_KEY = "grant_type";
    private static final String FORM_CLIENT_SECRET_KEY = "client_secret";
    private static final String FORM_CLIENT_ID_KEY = "client_id";
    private static final String FORM_USERNAME_KEY = "username";
    private static final String FORM_PASSWORD_KEY = "password";
    private static final long DEFAULT_EXPIRES_IN_SECONDS = 120L;

    private final ExternalServiceInvoker externalServiceInvoker;
    private final ChaparClientProperties chaparClientProperties;
    private final ChaparTokenCacheService tokenCache;

    public ChaparTokenManager(ExternalServiceInvoker externalServiceInvoker,
            ChaparClientProperties chaparClientProperties, ChaparTokenCacheService tokenCache) {
        this.externalServiceInvoker = externalServiceInvoker;
        this.chaparClientProperties = chaparClientProperties;
        this.tokenCache = tokenCache;
        validateConfig();
    }

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

        String url = externalServiceInvoker.generateUrl(ChaparUrl.LOGIN.getUrl());
        try {
            ResponseEntity<GetTokenResponseDto> response = externalServiceInvoker.getClient()
                    .post().uri(url).headers(tokenRequestHeaders()).body(buildTokenRequestForm()).retrieve()
                    .toEntity(GetTokenResponseDto.class);
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

    private Consumer<HttpHeaders> tokenRequestHeaders() {
        return headers -> {
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        };
    }

    private MultiValueMap<String, String> buildTokenRequestForm() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add(FORM_CLIENT_ID_KEY, chaparClientProperties.getClientId());
        form.add(FORM_CLIENT_SECRET_KEY, chaparClientProperties.getClientSecret());
        form.add(FORM_GRANT_TYPE_KEY, chaparClientProperties.getGrantType().getValue());
        if (chaparClientProperties.getGrantType().equals(ChaparGrantType.PASSWORD)) {
            form.add(FORM_PASSWORD_KEY, chaparClientProperties.getPassword());
            form.add(FORM_USERNAME_KEY, chaparClientProperties.getUsername());
        }
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
        if (chaparClientProperties.getGrantType() == null) {
            throw new ChaparMessagingValidationException("messaging-client.chapar.grant-type is required");
        }
        if (chaparClientProperties.getGrantType().equals(ChaparGrantType.PASSWORD)) {
            if (!StringUtils.hasText(chaparClientProperties.getPassword()) || !StringUtils.hasText(
                    chaparClientProperties.getUsername())) {
                throw new ChaparMessagingValidationException(
                        "messaging-client.chapar.username and messaging-client.chapar.password are required");
            }
        }
    }
}
