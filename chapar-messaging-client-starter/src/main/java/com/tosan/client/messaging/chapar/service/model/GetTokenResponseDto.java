package com.tosan.client.messaging.chapar.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Amirhossein Zamanzade
 * @since 5/16/26
 */
@Data
public class GetTokenResponseDto {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private Long expiresIn;
    @JsonProperty("refresh_expires_in")
    private Long refreshExpiresIn;
    @JsonProperty("not-before-policy")
    private Long notBeforePolicy;
    private String scope;
    @JsonProperty("token_type")
    private String tokenType;
}
