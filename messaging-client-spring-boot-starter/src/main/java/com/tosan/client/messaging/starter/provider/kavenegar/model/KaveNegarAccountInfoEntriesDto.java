package com.tosan.client.messaging.starter.provider.kavenegar.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KaveNegarAccountInfoEntriesDto {
    @JsonProperty("remaincredit")
    private Long remainCredit;
    @JsonProperty("expiredate")
    private Long expireDate;
    @JsonProperty("type")
    private String type;
}
