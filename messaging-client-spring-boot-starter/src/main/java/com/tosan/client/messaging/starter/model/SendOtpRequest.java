package com.tosan.client.messaging.starter.model;

import com.tosan.client.messaging.starter.model.enumeration.OtpMediaType;
import com.tosan.client.messaging.starter.model.enumeration.OtpType;
import lombok.Data;


/**
 * @author Ali Alimohammadi
 * @since 01/13/2026
 */
@Data
public class SendOtpRequest {

    /**
     * شماره مقصد - اجباری
     */
    private String destination;

    /**
     * نوعا OTP - اجباری
     */
    private OtpType otpType;

    /**
     * اطلاعات مورد استفاده در صورت استفاده از نوع OTP مقدار SIMPLE
     */
    private SimpleOtpDetailInfo simpleOtpDetailInfo;

    /**
     * اطلاعات مورد استفاده در صورت استفاده از نوع OTP مقدار TEMPLATE
     */
    private TemplateOtpDetailInfo templateOtpDetailInfo;

    /**
     * نوع ارسال پیام
     */
    private OtpMediaType type;
}
