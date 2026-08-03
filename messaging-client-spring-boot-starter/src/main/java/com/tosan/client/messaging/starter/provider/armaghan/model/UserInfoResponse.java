package com.tosan.client.messaging.starter.provider.armaghan.model;

import lombok.Data;

@Data
public class UserInfoResponse {
    private Sendonetomanymessage200ResponseErrorModel errorModel;
    private UserInfo userInfo;
}
