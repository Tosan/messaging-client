package com.tosan.client.messaging.starter.provider.kavenegar.model;

import lombok.Data;

/**
 * @author Ali Alimohammadi
 * @since 01/13/2026
 */
@Data
public class KaveNegarSendOtpRequestDto {
    private String receptor;
    private String type;
    private String token;
    private String token2;
    private String token3;
    private String token10;
    private String token20;
    private String template;
}
