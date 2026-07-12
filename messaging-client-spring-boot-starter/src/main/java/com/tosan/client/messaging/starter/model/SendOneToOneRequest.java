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
public class SendOneToOneRequest {
    private String content;
    private String destination;
}
