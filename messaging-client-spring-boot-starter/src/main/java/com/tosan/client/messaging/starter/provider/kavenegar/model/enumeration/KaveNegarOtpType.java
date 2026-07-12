package com.tosan.client.messaging.starter.provider.kavenegar.model.enumeration;

/**
 * @author Ali Alimohammadi
 * @since 1/13/26
 */
public enum KaveNegarOtpType {
    SMS("sms"),
    CALL("call");

    private final String value;

    KaveNegarOtpType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
