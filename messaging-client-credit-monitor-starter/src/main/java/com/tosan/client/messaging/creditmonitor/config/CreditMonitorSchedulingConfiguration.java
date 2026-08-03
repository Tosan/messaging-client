package com.tosan.client.messaging.creditmonitor.config;

import com.tosan.client.messaging.creditmonitor.scheduler.CreditMonitorScheduler;
import com.tosan.client.messaging.starter.service.MessagingService;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
public class CreditMonitorSchedulingConfiguration {

    @Bean
    public CreditMonitorScheduler creditMonitorScheduler(MessagingService messagingService,
                                                         CreditMonitorProperties properties) {
        return new CreditMonitorScheduler(messagingService, properties);
    }
}
