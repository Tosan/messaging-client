package com.tosan.client.messaging.starter.provider.kavenegar.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KaveNegarAccountInfoResponseDto {
    @JsonProperty("return")
    private ReturnInfo returnInfo;
    @JsonProperty("entries")
    private KaveNegarAccountInfoEntriesDto entries;
}
