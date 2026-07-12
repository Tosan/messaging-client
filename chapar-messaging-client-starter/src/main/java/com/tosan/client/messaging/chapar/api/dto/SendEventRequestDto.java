package com.tosan.client.messaging.chapar.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

/**
 * @author Amirhossein Zamanzade
 * @since 5/24/26
 */
@Getter
@Setter
public class SendEventRequestDto {

    /**
     * شماره موبایل مقصد - اجباری
     */
    private String destination;

    /**
     * شناسه الگو - اجباری
     */
    private String templateId;

    /**
     * پارامترهای موجود در الگو - اجباری
     */
    private Map<String, Object> parameters;

    /**
     * کلید پارامترهای امنیتی
     */
    private Set<String> securedParametersKeys;
}
