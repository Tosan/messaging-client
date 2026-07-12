package com.tosan.client.messaging.starter.service.assembler;

import com.tosan.client.messaging.starter.config.MessagingClientConfig;
import com.tosan.client.messaging.starter.model.*;
import com.tosan.client.messaging.starter.provider.armaghan.model.SendmanytomanymessageRequest;
import com.tosan.client.messaging.starter.provider.armaghan.model.Sendonetomanymessage200Response;
import com.tosan.client.messaging.starter.provider.armaghan.modelsabstraction.ClientConfigurable;
import com.tosan.client.messaging.starter.provider.armaghan.modelsabstraction.ClientConfigurableWithOriginator;
import com.tosan.client.messaging.starter.provider.handler.ArmaghanNegarStatus;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Ali Alimohammadi
 * @since 01/13/2026
 */
@RequiredArgsConstructor
public class ArmaghanNegarAssembler {
    private final MessagingClientConfig clientConfig;

    public void setClientConfig(ClientConfigurable request) {
        request.setUsername(clientConfig.getAuthorization().getUsername());
        request.setPassword(clientConfig.getAuthorization().getPassword());

        if (request instanceof ClientConfigurableWithOriginator) {
            ((ClientConfigurableWithOriginator) request).setOriginator(clientConfig.getArmaghanNegar().getOriginator());
        }
    }

    public SendmanytomanymessageRequest toManyToManyRequest(SendOneToOneRequest sendOneToOneRequest) {
        SendmanytomanymessageRequest request = new SendmanytomanymessageRequest();
        request.addContentsItem(sendOneToOneRequest.getContent());
        request.addDestinationsItem(sendOneToOneRequest.getDestination());
        setClientConfig(request);
        return request;
    }

    public SendmanytomanymessageRequest toManyToManyRequest(SendOtpRequest sendOtpRequest) {
        SendmanytomanymessageRequest request = new SendmanytomanymessageRequest();
        SimpleOtpDetailInfo simpleOtpDetailInfo = sendOtpRequest.getSimpleOtpDetailInfo();
        if (simpleOtpDetailInfo != null) {
            request.addContentsItem(simpleOtpDetailInfo.getOtpMessageContent());
        }
        request.addDestinationsItem(sendOtpRequest.getDestination());
        setClientConfig(request);
        return request;
    }

    public SendOneToOneResponse toOneToOneResponse(Sendonetomanymessage200Response sendonetomanymessage200Response,
            String destination) {
        SendOneToOneResponse response = new SendOneToOneResponse();
        sendonetomanymessage200Response.getReferences().stream().findFirst().ifPresent(reference -> {
            response.setResponseModel(referenceToResponseModel(
                    reference, destination));
        });
        return response;
    }

    public SendmanytomanymessageRequest toManyToManyRequest(SendOneToManyRequest sendOneToManyRequest) {
        SendmanytomanymessageRequest request = new SendmanytomanymessageRequest();
        request.setDestinations(sendOneToManyRequest.getDestinations());
        List<String> contents = IntStream.range(0, sendOneToManyRequest.getDestinations().size())
                .mapToObj(index -> sendOneToManyRequest.getContent())
                .toList();
        request.setContents(contents);
        setClientConfig(request);
        return request;
    }

    public SendOneToManyResponse toOneToManyResponse(Sendonetomanymessage200Response sendonetomanymessage200Response,
            List<String> destinations) {
        SendOneToManyResponse response = new SendOneToManyResponse();

        List<MessageResponseModel> messageResponseModels = IntStream.range(0, sendonetomanymessage200Response
                        .getReferences().size())
                .mapToObj(i -> referenceToResponseModel(sendonetomanymessage200Response
                        .getReferences().get(i), destinations.get(i)))
                .toList();

        response.setResponseModels(messageResponseModels);

        return response;
    }

    public SendmanytomanymessageRequest toManyToManyRequest(SendManyToManyRequest sendManyToManyRequest) {
        SendmanytomanymessageRequest request = new SendmanytomanymessageRequest();
        request.setContents(sendManyToManyRequest.getMessageRequests().stream().map(MessageRequest::getContent)
                .toList());
        request.setDestinations(sendManyToManyRequest.getMessageRequests().stream().map(MessageRequest::getDestination)
                .toList());
        setClientConfig(request);
        return request;
    }

    public SendManyToManyResponse toManyToManyResponse(Sendonetomanymessage200Response sendonetomanymessage200Response,
            List<String> destinations) {
        SendManyToManyResponse response = new SendManyToManyResponse();

        List<MessageResponseModel> messageResponseModels = IntStream.range(0, sendonetomanymessage200Response
                        .getReferences().size())
                .mapToObj(i -> referenceToResponseModel(sendonetomanymessage200Response
                        .getReferences().get(i), destinations.get(i)))
                .toList();

        response.setResponseModels(messageResponseModels);

        return response;
    }

    public MessageResponseModel referenceToResponseModel(BigDecimal reference, String destination) {
        MessageResponseModel responseModel = new MessageResponseModel();
        responseModel.setDestination(destination);
        checkError(responseModel, reference);
        return responseModel;
    }

    private void checkError(MessageResponseModel responseModel, BigDecimal reference) {
        if (reference.compareTo(BigDecimal.ZERO) > 0) {
            responseModel.setMessage("درخواست با موفقیت انجام شد: " + reference);
            responseModel.setReference(reference.toString());
            responseModel.setSuccess(true);
            responseModel.setErrorCode("0");
        } else {
            ArmaghanNegarStatus armaghanNegarStatus = ArmaghanNegarStatus.findByValue(reference.toString());
            responseModel.setMessage(armaghanNegarStatus.getMessage());
            responseModel.setSuccess(false);
            responseModel.setErrorCode(reference.toString());
        }
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
}
