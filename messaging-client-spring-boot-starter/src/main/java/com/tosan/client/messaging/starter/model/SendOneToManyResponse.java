package com.tosan.client.messaging.starter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author saber mortazavi
 * @since 12/07/2024
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SendOneToManyResponse {
    private List<MessageResponseModel> responseModels;
}
