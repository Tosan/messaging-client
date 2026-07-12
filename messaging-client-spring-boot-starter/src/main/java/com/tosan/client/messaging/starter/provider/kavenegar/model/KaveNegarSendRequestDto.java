package com.tosan.client.messaging.starter.provider.kavenegar.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Ali Alimohammadi
 * @since 01/13/2026
 */
@Data
public class KaveNegarSendRequestDto {

    @JsonProperty("receptor")
    private String receptor;

    @JsonProperty("message")
    private String message;

    @JsonProperty("sender")
    private String sender;
}
