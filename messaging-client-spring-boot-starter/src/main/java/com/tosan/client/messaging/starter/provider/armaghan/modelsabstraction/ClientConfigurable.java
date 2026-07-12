package com.tosan.client.messaging.starter.provider.armaghan.modelsabstraction;

/**
 * @author saber mortazavi
 * @since 12/07/2024
 */
public interface ClientConfigurable {

    void setUsername(String username);

    void setPassword(String password);

    String getUsername();

    String getPassword();
}
