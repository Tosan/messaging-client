package com.tosan.client.messaging.creditmonitor.config;

import com.tosan.client.messaging.starter.configuration.ArmaghanNegarClientConfiguration;
import com.tosan.client.messaging.starter.configuration.KaveNegarClientConfiguration;
import com.tosan.client.messaging.starter.service.MessagingService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@AutoConfigureAfter({
        KaveNegarClientConfiguration.class,
        ArmaghanNegarClientConfiguration.class
})
@ConditionalOnProperty(prefix = "messaging-client.credit-monitor", name = "enabled",
        havingValue = "true", matchIfMissing = true)
@ConditionalOnSingleCandidate(MessagingService.class)
@EnableConfigurationProperties(CreditMonitorProperties.class)
@Import(CreditMonitorSchedulingConfiguration.class)
public class CreditMonitorAutoConfiguration {
}
