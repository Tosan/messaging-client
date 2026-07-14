package com.tosan.client.messaging.starter.service.impl;

import com.tosan.client.http.starter.impl.feign.ExternalServiceInvoker;
import com.tosan.client.messaging.starter.exception.MessagingException;
import com.tosan.client.messaging.starter.exception.business.InvalidMediaTypeException;
import com.tosan.client.messaging.starter.exception.business.InvalidOtpTypeException;
import com.tosan.client.messaging.starter.model.*;
import com.tosan.client.messaging.starter.model.enumeration.OtpMediaType;
import com.tosan.client.messaging.starter.provider.armaghan.facade.WebserviceApi;
import com.tosan.client.messaging.starter.provider.armaghan.model.SendmanytomanymessageRequest;
import com.tosan.client.messaging.starter.provider.armaghan.model.Sendonetomanymessage200Response;
import com.tosan.client.messaging.starter.provider.handler.ArmaghanNegarResponseHandler;
import com.tosan.client.messaging.starter.service.MessagingService;
import com.tosan.client.messaging.starter.service.assembler.ArmaghanNegarAssembler;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import static com.tosan.client.messaging.starter.model.enumeration.OtpType.SIMPLE;

/**
 * @author saber mortazavi
 * @since 12/07/2024
 */
@RequiredArgsConstructor
public class ArmaghanNegarMessagingService implements MessagingService {
    private final ExternalServiceInvoker<WebserviceApi> serviceInvoker;
    private final ArmaghanNegarAssembler armaghanNegarAssembler;
    private final ArmaghanNegarResponseHandler armaghanNegarResponseHandler;

    @Override
    public SendOneToManyResponse sendOneToManyMessage(SendOneToManyRequest request) throws MessagingException {
        SendmanytomanymessageRequest sendmanytomanymessageRequest = armaghanNegarAssembler.toManyToManyRequest(request);
        Sendonetomanymessage200Response sendonetomanymessage200Response = serviceInvoker
                .getClient().sendmanytomanymessage(sendmanytomanymessageRequest);
        armaghanNegarResponseHandler.handleError(sendonetomanymessage200Response);
        return armaghanNegarAssembler.toOneToManyResponse(sendonetomanymessage200Response, sendmanytomanymessageRequest.getDestinations());
    }

    @Override
    public SendManyToManyResponse sendManyToManyMessage(SendManyToManyRequest request) throws MessagingException {
        SendmanytomanymessageRequest sendmanytomanymessageRequest = armaghanNegarAssembler.toManyToManyRequest(request);
        Sendonetomanymessage200Response sendonetomanymessage200Response = serviceInvoker
                .getClient().sendmanytomanymessage(sendmanytomanymessageRequest);
        armaghanNegarResponseHandler.handleError(sendonetomanymessage200Response);
        return armaghanNegarAssembler.toManyToManyResponse(sendonetomanymessage200Response, sendmanytomanymessageRequest.getDestinations());
    }

    @Override
    public SendOneToOneResponse sendOneToOneMessage(SendOneToOneRequest request) throws MessagingException {
        SendmanytomanymessageRequest sendmanytomanymessageRequest = armaghanNegarAssembler.toManyToManyRequest(request);
        Sendonetomanymessage200Response sendonetomanymessage200Response = serviceInvoker
                .getClient().sendmanytomanymessage(sendmanytomanymessageRequest);
        armaghanNegarResponseHandler.handleError(sendonetomanymessage200Response);
        SendOneToOneResponse oneToOneResponse = armaghanNegarAssembler.toOneToOneResponse(sendonetomanymessage200Response, request.getDestination());
        armaghanNegarResponseHandler.oneToOneHandleError(oneToOneResponse.getResponseModel().getErrorCode());
        return oneToOneResponse;
    }

    @Override
    public SendOtpResponse sendOtpMessage(SendOtpRequest request) throws MessagingException {
        if (SIMPLE.equals(request.getOtpType())) {
            if (request.getType() != null && Objects.equals(OtpMediaType.CALL, request.getType())) {
                throw new InvalidMediaTypeException("Unsupported media type: " + OtpMediaType.CALL);
            }
            SendmanytomanymessageRequest sendmanytomanymessageRequest = armaghanNegarAssembler.toManyToManyRequest(request);
            Sendonetomanymessage200Response sendonetomanymessage200Response = serviceInvoker
                    .getClient().sendmanytomanymessage(sendmanytomanymessageRequest);
            armaghanNegarResponseHandler.handleError(sendonetomanymessage200Response);
            SendOneToOneResponse oneToOneResponse = armaghanNegarAssembler.toOneToOneResponse(sendonetomanymessage200Response, request.getDestination());
            armaghanNegarResponseHandler.oneToOneHandleError(oneToOneResponse.getResponseModel().getErrorCode());
            return armaghanNegarAssembler.toSendOtpResponse(oneToOneResponse);
        } else {
            throw new InvalidOtpTypeException("Unsupported OTP type: " + request.getOtpType());
        }
    }
}
