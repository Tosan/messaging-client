package com.tosan.client.messaging.starter.model;

import lombok.Data;

/**
 * @author Ali Alimohammadi
 * @since 1/14/26
 */
@Data
public class SimpleOtpDetailInfo {

    /**
     * در صورتی که پروایدر غیر از کاوه نگار را انتخاب کرده اید بایستی متن پیامک خود را در این فیلد ارسال کنید
     */
    private String otpMessageContent;

}
