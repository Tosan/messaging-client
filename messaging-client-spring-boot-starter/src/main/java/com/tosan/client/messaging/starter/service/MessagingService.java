package com.tosan.client.messaging.starter.service;

import com.tosan.client.messaging.starter.exception.MessagingException;
import com.tosan.client.messaging.starter.model.*;

public interface MessagingService {

    SendOneToManyResponse sendOneToManyMessage(SendOneToManyRequest request) throws MessagingException;

    SendManyToManyResponse sendManyToManyMessage(SendManyToManyRequest request) throws MessagingException;

    SendOneToOneResponse sendOneToOneMessage(SendOneToOneRequest request) throws MessagingException;

    SendOtpResponse sendOtpMessage(SendOtpRequest request) throws MessagingException;
}
