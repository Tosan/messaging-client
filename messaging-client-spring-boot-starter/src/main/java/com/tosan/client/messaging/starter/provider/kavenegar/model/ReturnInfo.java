package com.tosan.client.messaging.starter.provider.kavenegar.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Ali Alimohammadi
 * @since 1/13/26
 */
@Data
public class ReturnInfo {

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;
}
