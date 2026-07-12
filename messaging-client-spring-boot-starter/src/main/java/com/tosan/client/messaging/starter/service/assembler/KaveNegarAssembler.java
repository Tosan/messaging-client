package com.tosan.client.messaging.starter.service.assembler;

import com.tosan.client.messaging.starter.model.*;
import com.tosan.client.messaging.starter.model.enumeration.OtpMediaType;
import com.tosan.client.messaging.starter.provider.kavenegar.model.EntryInfo;
import com.tosan.client.messaging.starter.provider.kavenegar.model.KaveNegarResponseDto;
import com.tosan.client.messaging.starter.provider.kavenegar.model.KaveNegarSendOtpRequestDto;
import com.tosan.client.messaging.starter.provider.kavenegar.model.KaveNegarSendRequestDto;
import com.tosan.client.messaging.starter.provider.kavenegar.model.enumeration.KaveNegarOtpType;
import lombok.AllArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author Ali Alimohammadi
 * @since 01/13/2026
 */
@AllArgsConstructor
public class KaveNegarAssembler {

    public KaveNegarSendRequestDto toKaveNegarSendRequestDto(SendOneToOneRequest request, String sender) {
        if (request == null) {
            return null;
        }
        KaveNegarSendRequestDto sendRequestDto = new KaveNegarSendRequestDto();
        sendRequestDto.setReceptor(request.getDestination());
        sendRequestDto.setMessage(request.getContent());
        if (StringUtils.hasText(sender)) {
            sendRequestDto.setSender(sender);
        }
        return sendRequestDto;
    }

    public KaveNegarSendRequestDto toKaveNegarSendRequestDto(SendOneToManyRequest request, String sender) {
        if (request == null) {
            return null;
        }
        KaveNegarSendRequestDto sendRequestDto = new KaveNegarSendRequestDto();
        if (!CollectionUtils.isEmpty(request.getDestinations())) {
            StringBuilder receptors = new StringBuilder();
            request.getDestinations().forEach(destination -> {
                receptors.append(destination);
                receptors.append(",");
            });
            receptors.deleteCharAt(receptors.length() - 1);
            sendRequestDto.setReceptor(receptors.toString());
        }

        sendRequestDto.setMessage(request.getContent());
        if (StringUtils.hasText(sender)) {
            sendRequestDto.setSender(sender);
        }
        return sendRequestDto;
    }

    public SendOneToOneResponse toSendOneToOneResponse(KaveNegarResponseDto responseDto) {
        if (responseDto == null) {
            return null;
        }
        SendOneToOneResponse sendOneToOneResponse = new SendOneToOneResponse();
        MessageResponseModel responseModel = new MessageResponseModel();
        List<EntryInfo> entries = responseDto.getEntries();
        if (!CollectionUtils.isEmpty(entries)) {
            entries.stream().findFirst().ifPresent(entryInfo -> {
                responseModel.setDestination(entryInfo.getReceptor());
                responseModel.setMessage(entryInfo.getMessage());
                responseModel.setSuccess(entryInfo.getStatus() == 1);
                responseModel.setReference(entryInfo.getMessageid().toString());
                sendOneToOneResponse.setResponseModel(responseModel);
            });
        }
        return sendOneToOneResponse;
    }

    public KaveNegarSendOtpRequestDto toKaveNegarSendOtpRequestDto(SendOtpRequest request) {
        if (request == null) {
            return null;
        }
        KaveNegarSendOtpRequestDto kaveNegarSendOtpRequestDto = new KaveNegarSendOtpRequestDto();
        TemplateOtpDetailInfo templateOtpDetailInfo = request.getTemplateOtpDetailInfo();
        if (templateOtpDetailInfo != null) {
            kaveNegarSendOtpRequestDto.setReceptor(request.getDestination());
            kaveNegarSendOtpRequestDto.setType(toKaveNegarOtpType(request.getType()));
            kaveNegarSendOtpRequestDto.setTemplate(templateOtpDetailInfo.getTemplateId());
            kaveNegarSendOtpRequestDto.setToken(templateOtpDetailInfo.getToken());
            kaveNegarSendOtpRequestDto.setToken2(templateOtpDetailInfo.getToken2());
            kaveNegarSendOtpRequestDto.setToken3(templateOtpDetailInfo.getToken3());
            kaveNegarSendOtpRequestDto.setToken10(templateOtpDetailInfo.getToken10());
            kaveNegarSendOtpRequestDto.setToken20(templateOtpDetailInfo.getToken20());
        }
        return kaveNegarSendOtpRequestDto;
    }

    private String toKaveNegarOtpType(OtpMediaType type) {
        if (type == null) {
            return null;
        }
        return switch (type) {
            case SMS -> KaveNegarOtpType.SMS.getValue();
            case CALL -> KaveNegarOtpType.CALL.getValue();
        };
    }

    public SendOtpResponse toSendOtpResponse(KaveNegarResponseDto responseDto) {
        if (responseDto == null) {
            return null;
        }
        SendOtpResponse sendOtpResponse = new SendOtpResponse();
        MessageResponseModel responseModel = new MessageResponseModel();
        List<EntryInfo> entries = responseDto.getEntries();
        if (!CollectionUtils.isEmpty(entries)) {
            entries.stream().findFirst().ifPresent(entryInfo -> {
                responseModel.setDestination(entryInfo.getReceptor());
                responseModel.setMessage(entryInfo.getMessage());
                responseModel.setSuccess(entryInfo.getStatus() == 1);
                responseModel.setReference(entryInfo.getMessageid().toString());
                sendOtpResponse.setResponseModel(responseModel);
            });

        }
        return sendOtpResponse;
    }

    public SendOtpResponse toSendOtpResponse(SendOneToOneResponse responseDto) {
        if (responseDto == null) {
            return null;
        }
        SendOtpResponse sendOtpResponse = new SendOtpResponse();
        MessageResponseModel resultResponseModel = new MessageResponseModel();
        MessageResponseModel responseModel = responseDto.getResponseModel();
        if (responseModel != null) {
            resultResponseModel.setDestination(responseModel.getDestination());
            resultResponseModel.setMessage(responseModel.getMessage());
            resultResponseModel.setSuccess(responseModel.isSuccess());
            resultResponseModel.setReference(responseModel.getReference());
            sendOtpResponse.setResponseModel(resultResponseModel);
        }
        return sendOtpResponse;
    }

    public SendOneToManyResponse toSendOneToManyResponse(KaveNegarResponseDto responseDto) {
        return null;
    }

    public KaveNegarSendRequestDto toSendOneToOneRequest(SendOtpRequest request, String sender) {
        if (request == null) {
            return null;
        }
        SimpleOtpDetailInfo simpleOtpDetailInfo = request.getSimpleOtpDetailInfo();
        KaveNegarSendRequestDto sendRequestDto = new KaveNegarSendRequestDto();
        if (simpleOtpDetailInfo != null) {
            sendRequestDto.setMessage(simpleOtpDetailInfo.getOtpMessageContent());
            sendRequestDto.setReceptor(request.getDestination());

        }
        if (StringUtils.hasText(sender)) {
            sendRequestDto.setSender(sender);
        }
        return sendRequestDto;
    }
}
