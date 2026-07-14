package com.tosan.client.messaging.starter.service.impl;

import com.tosan.client.messaging.starter.exception.MessagingException;
import com.tosan.client.messaging.starter.exception.business.InvalidOtpTypeException;
import com.tosan.client.messaging.starter.model.*;
import com.tosan.client.messaging.starter.model.enumeration.OtpMediaType;
import com.tosan.client.messaging.starter.model.enumeration.OtpType;
import com.tosan.client.messaging.starter.provider.kavenegar.invoker.KaveNegarInvoker;
import com.tosan.client.messaging.starter.provider.kavenegar.model.KaveNegarResponseDto;
import com.tosan.client.messaging.starter.provider.kavenegar.model.KaveNegarSendOtpRequestDto;
import com.tosan.client.messaging.starter.provider.kavenegar.model.KaveNegarSendRequestDto;
import com.tosan.client.messaging.starter.service.MessagingService;
import com.tosan.client.messaging.starter.service.assembler.KaveNegarAssembler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import static com.tosan.client.messaging.starter.model.enumeration.OtpType.SIMPLE;
import static com.tosan.client.messaging.starter.model.enumeration.OtpType.TEMPLATE;

/**
 * @author Ali Alimohammadi
 * @since 01/13/2026
 */
@RequiredArgsConstructor
@Slf4j
public class KaveNegarMessagingService implements MessagingService {
    private static final String SEND_URL = "/sms/send.json";
    private static final String LOOKUP_URL = "/verify/lookup.json";
    private static final String MAKE_TTS_URL = "/call/maketts.json";
    private final KaveNegarInvoker kaveNegarInvoker;
    private final KaveNegarAssembler kaveNegarAssembler;

    @Override
    public SendOneToManyResponse sendOneToManyMessage(SendOneToManyRequest request) throws MessagingException {
        KaveNegarSendRequestDto sendRequestDto = kaveNegarAssembler.toKaveNegarSendRequestDto(request,
                kaveNegarInvoker.getMessagingClientConfig().getKaveNegar().getOriginator());

        KaveNegarResponseDto responseDto = doSendMessage(SEND_URL, sendRequestDto);
        return kaveNegarAssembler.toSendOneToManyResponse(responseDto);
    }

    @Override
    public SendManyToManyResponse sendManyToManyMessage(SendManyToManyRequest request) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SendOneToOneResponse sendOneToOneMessage(SendOneToOneRequest request) throws MessagingException {
        KaveNegarSendRequestDto sendRequestDto = kaveNegarAssembler.toKaveNegarSendRequestDto(request,
                kaveNegarInvoker.getMessagingClientConfig().getKaveNegar().getOriginator());

        KaveNegarResponseDto responseDto = doSendMessage(SEND_URL, sendRequestDto);
        return kaveNegarAssembler.toSendOneToOneResponse(responseDto);
    }

    @Override
    public SendOtpResponse sendOtpMessage(SendOtpRequest request) throws MessagingException {
        OtpType otpType = request.getOtpType();
        if (TEMPLATE.equals(otpType)) {
            KaveNegarSendOtpRequestDto sendRequestDto = kaveNegarAssembler.toKaveNegarSendOtpRequestDto(request);
            UriComponentsBuilder uriComponentsBuilder =
                    UriComponentsBuilder.fromUriString(kaveNegarInvoker.generateUrl(LOOKUP_URL));
            uriComponentsBuilder.query(kaveNegarInvoker.createQueryParamString(sendRequestDto));
            String url = uriComponentsBuilder.build().toUriString();
            KaveNegarResponseDto response = kaveNegarInvoker.getClient().get().uri(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .retrieve()
                    .body(KaveNegarResponseDto.class);
            return kaveNegarAssembler.toSendOtpResponse(response);
        } else if (SIMPLE.equals(otpType)) {
            KaveNegarSendRequestDto kaveNegarSendRequestDto = kaveNegarAssembler.toSendOneToOneRequest(request,
                    kaveNegarInvoker.getMessagingClientConfig().getKaveNegar().getOriginator());
            KaveNegarResponseDto responseDto = switch (request.getType() == null ? OtpMediaType.SMS : request.getType()) {
                case SMS -> doSendMessage(SEND_URL, kaveNegarSendRequestDto);
                case CALL -> doSendMessage(MAKE_TTS_URL, kaveNegarSendRequestDto);
            };
            return kaveNegarAssembler.toSendOtpResponse(responseDto);
        } else {
            throw new InvalidOtpTypeException("Unsupported OTP type: " + request.getOtpType());
        }
    }

    private KaveNegarResponseDto doSendMessage(String baseUrl, KaveNegarSendRequestDto sendRequestDto) {
        UriComponentsBuilder uriComponentsBuilder =
                UriComponentsBuilder.fromUriString(kaveNegarInvoker.generateUrl(baseUrl));
        uriComponentsBuilder.query(kaveNegarInvoker.createQueryParamString(sendRequestDto));
        String url = uriComponentsBuilder.build().encode().toUriString();
        return kaveNegarInvoker.getClient().post().uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve()
                .body(KaveNegarResponseDto.class);
    }
}
