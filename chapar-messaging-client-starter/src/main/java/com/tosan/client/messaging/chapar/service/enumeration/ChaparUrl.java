package com.tosan.client.messaging.chapar.service.enumeration;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Amirhossein Zamanzade
 * @since 5/17/26
 */
@RequiredArgsConstructor
@Getter
public enum ChaparUrl {

    LOGIN("/auth/token"),
    SEND_EVENT("/event/external/send");

    final String url;
}
