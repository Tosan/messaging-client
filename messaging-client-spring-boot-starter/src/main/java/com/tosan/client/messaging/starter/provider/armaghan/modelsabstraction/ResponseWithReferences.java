package com.tosan.client.messaging.starter.provider.armaghan.modelsabstraction;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author saber mortazavi
 * @since 12/07/2024
 */
public interface ResponseWithReferences {

    List<BigDecimal> getReferences();
}
