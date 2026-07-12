package com.tosan.client.messaging.chapar.service.model;

import com.tosan.client.messaging.chapar.service.model.enumeration.ChaparContactType;
import com.tosan.client.messaging.chapar.service.model.enumeration.ChaparSendType;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Amirhossein Zamanzade
 * @since 5/16/26
 */
@Data
@Builder(toBuilder = true)
public class SendEventRequestDto {
    private String type;
    private ChaparContactType contactType;
    private String contactCode;
    private Map<String, Object> parameters;
    private Map<String, Object> securedParameters;
    private Map<String, Object> overrides;
    private Map<String, String> cc;
    private List<String> attachments;
    private String uuid;
    private ChaparSendType sendType;
}
