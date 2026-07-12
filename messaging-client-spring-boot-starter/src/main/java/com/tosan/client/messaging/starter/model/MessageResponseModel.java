package com.tosan.client.messaging.starter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author saber mortazavi
 * @since 12/07/2024
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageResponseModel {
    private String destination;
    private boolean success;
    private String message;
    private String errorCode;
    private String reference;
}
