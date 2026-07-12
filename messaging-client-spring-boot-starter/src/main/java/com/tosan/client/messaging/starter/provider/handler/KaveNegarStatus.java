package com.tosan.client.messaging.starter.provider.handler;

import lombok.Getter;

import java.util.stream.Stream;

/**
 * @author Ali Alimohammadi
 * @since 1/14/26
 */
@Getter
public enum KaveNegarStatus {
    SUCCESS(200, "درخواست تایید"),
    USER_BLOCKED(401, "حساب کاربری غیرفعال شده است"),
    FAILED(402, "عملیات ناموفق"),
    BAD_REQUEST(400, "پارامترها ناقص هستند"),
    BAD_API_KEY(403, "کد شناسائی API-Key معتبر نمی‌باشد"),
    NOT_FOUND(404, " متد نامشخص است"),
    INVALID_METHOD(405, " متد Get/Post اشتباه است"),
    REQUIRED_PARAM(406, " پارامترهای اجباری خالی ارسال شده اند"),
    FORBIDDEN(407, " دسترسی به اطلاعات مورد نظر برای شما امکان پذیر نیست"),
    CONFLICT(409, " سرور قادر به پاسخگوئی نیست بعدا تلاش کنید"),
    INVALID_RECEPTOR(411, " دریافت کننده نامعتبر است"),
    INVALID_SENDER(412, " ارسال کننده نامعتبر است"),
    MAX_LENGTH_MESSAGE(413, " پیام خالی است و یا طول پیام بیش از حد مجاز می‌باشد.حداکثر طول کل متن پیامک 900کاراکتر می باشد"),
    MAX_REQUEST(414, " حجم درخواست بیشتر از حد مجاز است ،ارسال پیامک:هر فراخوانی حداکثر 200رکورد و کنترل وضعیت:هر فراخوانی 500رکورد"),
    INVALID_COUNT(415, " اندیس شروع بزرگ تر از کل تعداد شماره های مورد نظر است"),
    INVALID_IP(416, " IP سرویس مبدا با تنظیمات مطابقت ندارد"),
    INVALID_DATE(417, " تاریخ ارسال اشتباه است و فرمت آن صحیح نمی باشد."),
    LOW_CREDIT(418, "اعتبار شما کافی نمی‌باشد"),
    INVALID_ARRAY_LENGTH(419, " طول آرایه متن و گیرنده و فرستنده هم اندازه نیست"),
    LINK_NOT_ALLOWED(420, " استفاده از لینک در متن پیام برای شما محدود شده است"),
    INVALID_CHARACTER(422, " داده ها به دلیل وجود کاراکتر نامناسب قابل پردازش نیست"),
    INVALID_PATTERN(424, " الگوی مورد نظر پیدا نشد"),
    ADVANCED_SERVICE(426, " استفاده از این متد نیازمند سرویس پیشرفته می‌باشد"),
    PERMISSION_DENIED(427, " استفاده از این خط نیازمند ایجاد سطح دسترسی می باشد"),
    CALL_MESSAGE_NOT_REACHABLE(428, " ارسال کد از طریق تماس تلفنی امکان پذیر نیست"),
    IP_LIMITED(429, " IP محدود شده است"),
    INVALID_CODE(431, " ساختار کد صحیح نمی‌باشد"),
    CODE_NOT_FOUND(432, " پارامتر کد در متن پیام پیدا نشد"),
    RATE_LIMIT(451, " فراخوانی بیش از حد در بازه زمانی مشخص IP محدود شده"),
    TEST_SERVICE(501, " فقط امکان ارسال پیام تست به شماره صاحب حساب کاربری وجود دارد");

    private final Integer code;
    private final String message;

    KaveNegarStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static KaveNegarStatus findByValue(Integer value) {
        return (value != null) ? Stream.of(KaveNegarStatus.values())
                .filter(v -> (value.equals(v.getCode())))
                .findFirst().orElse(FAILED) : FAILED;
    }
}
