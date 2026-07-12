package com.tosan.client.messaging.starter.model;

import lombok.Data;

/**
 * @author Ali Alimohammadi
 * @since 1/14/26
 */
@Data
public class TemplateOtpDetailInfo {
    /**
     * نام الگو
     */
    private String templateId;

    /**
     * می تواند حاوی کلیه کاراکترهای فارسی، انگلیسی و عدد باشد، به جز فضای خالی (Space)،حداکثر تعداد کاراکتر 100
     */
    private String token;

    /**
     * می تواند حاوی کلیه کاراکترهای فارسی، انگلیسی و عدد باشد، به جز فضای خالی (Space)،حداکثر تعداد کاراکتر 100
     */
    private String token2;

    /**
     * می تواند حاوی کلیه کاراکترهای فارسی، انگلیسی و عدد باشد، به جز فضای خالی (Space)،حداکثر تعداد کاراکتر 100
     */
    private String token3;

    /**
     * می تواند حاوی کلیه کاراکترهای فارسی، انگلیسی و عدد باشد، میتواند حاوی 5 فضای خالی (Space) باشد،حداکثر تعداد کاراکتر 100
     */
    private String token10;

    /**
     * می تواند حاوی کلیه کاراکترهای فارسی، انگلیسی و عدد باشد، میتواند حاوی 5 فضای خالی (Space) باشد،حداکثر تعداد کاراکتر 100
     */
    private String token20;
}
