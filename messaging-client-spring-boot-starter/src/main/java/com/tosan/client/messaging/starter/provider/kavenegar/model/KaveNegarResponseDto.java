package com.tosan.client.messaging.starter.provider.kavenegar.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Ali Alimohammadi
 * @since 01/13/2026
 */
@Data
public class KaveNegarResponseDto {

    @JsonProperty("return")
    private ReturnInfo returnInfo;

    @JsonProperty("entries")
    private List<EntryInfo> entries;
}
