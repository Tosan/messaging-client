package com.tosan.client.messaging.starter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author saber mortazavi
 * @since 12/07/2024
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageRequest {
    private String destination;
    private String content;
}
