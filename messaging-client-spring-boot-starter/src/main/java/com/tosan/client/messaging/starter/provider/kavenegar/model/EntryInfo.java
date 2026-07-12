package com.tosan.client.messaging.starter.provider.kavenegar.model;

import lombok.Data;

/**
 * @author Ali Alimohammadi
 * @since 1/13/26
 */
@Data
public class EntryInfo {
    private Long messageid;
    private String message;
    private Integer status;
    private String statustext;
    private String sender;
    private String receptor;
    private Long date;
    private Integer cost;
}
