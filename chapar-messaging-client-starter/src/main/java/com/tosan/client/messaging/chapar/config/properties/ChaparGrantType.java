package com.tosan.client.messaging.chapar.config.properties;

import lombok.Getter;

/**
 * @author Amirhossein Zamanzade
 * @since 9/2/26
 */
public enum ChaparGrantType {

    CLIENT_CREDENTIALS("client_credentials"),
    PASSWORD("password");

    @Getter
    private final String value;

    ChaparGrantType(String value) {
        this.value = value;
    }
}
