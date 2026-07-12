package com.tosan.client.messaging.starter.provider.armaghan.modelsabstraction;

/**
 * @author saber mortazavi
 * @since 12/07/2024
 */
public interface ClientConfigurableWithOriginator extends ClientConfigurable {

    void setOriginator(String originator);

    String getOriginator();
}
