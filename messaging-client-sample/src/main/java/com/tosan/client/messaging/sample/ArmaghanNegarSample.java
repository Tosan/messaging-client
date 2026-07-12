package com.tosan.client.messaging.sample;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tosan.client.messaging.chapar.api.ChaparMessagingService;
import com.tosan.client.messaging.chapar.api.dto.SendEventRequestDto;
import com.tosan.client.messaging.chapar.api.dto.SendEventResponseDto;
import com.tosan.client.messaging.starter.model.*;
import com.tosan.client.messaging.starter.model.enumeration.OtpMediaType;
import com.tosan.client.messaging.starter.model.enumeration.OtpType;
import com.tosan.client.messaging.starter.service.MessagingService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author saber mortazavi
 * @since 12/07/2024
 */
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
@SpringBootApplication(scanBasePackages = {"com.tosan.client.messaging"})
public class ArmaghanNegarSample implements CommandLineRunner {


    private final MessagingService messagingService;
    private final ChaparMessagingService chaparMessagingService;

    public static void main(String[] args) {
        new SpringApplicationBuilder(ArmaghanNegarSample.class)
                .web(WebApplicationType.NONE)
                .build()
                .run();
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper;
    }

    @Override
    public void run(String... args) {
//        sendVoiceOtpRequest();
//        sendTemplatedSms();
//        sendSimpleOtp();
//        sendOneToOneSms();
//        sendOneToManySms();
//        sendManyToManySms();
        sendChaparMessage();
    }

    private void sendChaparMessage() {
        SendEventRequestDto requestDto = new SendEventRequestDto();
        requestDto.setDestination("09100634241");
        requestDto.setTemplateId("templateId");
        requestDto.setParameters(Map.of("key1", "value1", "key2", "value2"));
        requestDto.setSecuredParametersKeys(Set.of("key1"));
        SendEventResponseDto sendEventResponseDto = chaparMessagingService.sendEvent(requestDto);
        System.out.println(sendEventResponseDto.getUuid());
    }

    private void sendManyToManySms() {
        SendManyToManyRequest sendManyToManyRequest = new SendManyToManyRequest(new ArrayList<>());
        sendManyToManyRequest.getMessageRequests().add(new MessageRequest("", "many to many"));
        sendManyToManyRequest.getMessageRequests().add(new MessageRequest("", "many to many"));
        try {
            SendManyToManyResponse sendManyToManyMessage = messagingService.sendManyToManyMessage(
                    sendManyToManyRequest);
            log.info(sendManyToManyMessage.toString());
        } catch (Exception e) {
            log.error("error: ", e);
        }
    }

    private void sendOneToManySms() {
        SendOneToManyRequest oneToManyRequest = new SendOneToManyRequest();
        oneToManyRequest.setContent("one to many");
        oneToManyRequest.setDestinations(List.of("", ""));
        try {
            SendOneToManyResponse sendOneToManyMessage = messagingService.sendOneToManyMessage(oneToManyRequest);
            log.info(sendOneToManyMessage.toString());
        } catch (Exception e) {
            log.error("error: ", e);
        }
    }

    private void sendOneToOneSms() {
        SendOneToOneRequest oneToOneRequest = new SendOneToOneRequest();
        oneToOneRequest.setContent("one to one");
        oneToOneRequest.setDestination("+989120453595");
        try {
            SendOneToOneResponse sendOneToOneMessage = messagingService.sendOneToOneMessage(oneToOneRequest);
            log.info(sendOneToOneMessage.toString());
        } catch (Exception e) {
            log.error("error: ", e);
        }
    }

    private void sendSimpleOtp() {
        SendOtpRequest simpleSendOtpRequest = new SendOtpRequest();
        simpleSendOtpRequest.setDestination("09120453595");
        SimpleOtpDetailInfo simpleOtpDetailInfo = new SimpleOtpDetailInfo();
        simpleOtpDetailInfo.setOtpMessageContent("رمز 1234");
        simpleSendOtpRequest.setSimpleOtpDetailInfo(simpleOtpDetailInfo);
        try {
            SendOtpResponse sendOtpResponse = messagingService.sendOtpMessage(simpleSendOtpRequest);
            log.info(sendOtpResponse.toString());
        } catch (Exception e) {
            log.error("error: ", e);
        }
    }

    private SendOtpRequest sendTemplatedSms() {
        SendOtpRequest sendTemplateOtpRequest = new SendOtpRequest();
        sendTemplateOtpRequest.setDestination("09120453595");
        sendTemplateOtpRequest.setOtpType(OtpType.TEMPLATE);
        TemplateOtpDetailInfo templateOtpDetailInfo = new TemplateOtpDetailInfo();
        templateOtpDetailInfo.setTemplateId("mahaan-otp");
        templateOtpDetailInfo.setToken("token");
        sendTemplateOtpRequest.setType(OtpMediaType.SMS);
        sendTemplateOtpRequest.setTemplateOtpDetailInfo(templateOtpDetailInfo);
        try {
            SendOtpResponse sendOtpResponse = messagingService.sendOtpMessage(sendTemplateOtpRequest);
            log.info(sendOtpResponse.toString());
        } catch (Exception e) {
            log.error("error: ", e);
        }
        return sendTemplateOtpRequest;
    }

    private void sendVoiceOtpRequest() {
        SendOtpRequest sendVoiceOtpRequest = new SendOtpRequest();
        sendVoiceOtpRequest.setDestination("09120453595");
        sendVoiceOtpRequest.setOtpType(OtpType.SIMPLE);
        sendVoiceOtpRequest.setType(OtpMediaType.CALL);
        SimpleOtpDetailInfo simpleVoiceOtpDetailInfo = new SimpleOtpDetailInfo();
        simpleVoiceOtpDetailInfo.setOtpMessageContent("1234");
        sendVoiceOtpRequest.setSimpleOtpDetailInfo(simpleVoiceOtpDetailInfo);
        try {
            SendOtpResponse sendOtpResponse = messagingService.sendOtpMessage(sendVoiceOtpRequest);
            log.info(sendOtpResponse.toString());
        } catch (Exception e) {
            log.error("error: ", e);
        }
    }
}
