package com.tosan.client.messaging.chapar.service.model;

import lombok.Data;

import java.util.Map;

/**
 * @author Amirhossein Zamanzade
 * @since 5/16/26
 */
@Data
public class ErrorObject {
    private String errorType;
    private String errorCode;
    private String message;
    private Map<String, Object> errorParam;
}
