package com.tosan.client.messaging.starter.provider.handler;

import lombok.Getter;

import java.util.stream.Stream;

/**
 * @author Ali Alimohammadi
 * @since 1/14/26
 */
@Getter
public enum ArmaghanNegarStatus {
    SUCCESS("0", "درخواست تایید"),
    AUTHENTICATE_FAILED("-101", "احراز هویت ناموفق. لطفاً اطلاعات ورود خود را بررسی کنید."),
    INVALID_SENDER("-103", "فرستنده نامعتبر است. لطفاً اطلاعات فرستنده را بررسی کنید."),
    LOW_CREDIT("-104", "اعتبار کافی نیست. لطفاً حساب خود را شارژ کنید."),
    BAD_REQUEST("-105", "درخواست ارسال شده ساختار نامعتبری دارد. لطفاً ساختار و پارامترهای درخواست را بررسی کنید."),
    INVALID_RECEPTOR("-107", "مقصد نامعتبر است. اطلاعات گیرنده را بررسی کرده و دوباره تلاش کنید."),
    INVALID_IP("-110", "آدرس IP نامعتبر است. لطفاً اطمینان حاصل کنید که درخواست از IP مجاز ارسال شده است."),
    SERVICE_IS_DISABLED("-119", "سرویس مورد نظر فعال نیست. لطفاً با پشتیبانی تماس بگیرید."),
    INTERNAL_SERVER_ERROR("-201", "خطای داخلی در سرور رخ داده است. لطفاً بعداً دوباره تلاش کنید.");

    private final String code;
    private final String message;

    ArmaghanNegarStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ArmaghanNegarStatus findByValue(String value) {
        return (value != null) ? Stream.of(ArmaghanNegarStatus.values())
                .filter(v -> (value.equals(v.getCode())))
                .findFirst().orElse(INTERNAL_SERVER_ERROR) : INTERNAL_SERVER_ERROR;
    }
}
