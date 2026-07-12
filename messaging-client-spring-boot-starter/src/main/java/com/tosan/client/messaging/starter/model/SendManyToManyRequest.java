package com.tosan.client.messaging.starter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author saber mortazavi
 * @since 12/07/2024
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SendManyToManyRequest {
    private List<MessageRequest> messageRequests;
}
