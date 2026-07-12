package com.tosan.client.messaging.chapar.api;


import com.tosan.client.messaging.chapar.api.dto.SendEventRequestDto;
import com.tosan.client.messaging.chapar.api.dto.SendEventResponseDto;

/**
 * @author Amirhossein Zamanzade
 * @since 5/24/26
 */
public interface ChaparMessagingService {

    SendEventResponseDto sendEvent(SendEventRequestDto sendEventRequestDto);
}
