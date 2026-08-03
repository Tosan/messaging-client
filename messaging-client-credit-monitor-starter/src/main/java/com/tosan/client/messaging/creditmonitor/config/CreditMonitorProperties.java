package com.tosan.client.messaging.creditmonitor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "messaging-client.credit-monitor")
public class CreditMonitorProperties {
    private boolean enabled;
    private String cron;
    private String timezone;
    private Long minimumCreditThresholdRial;
}
