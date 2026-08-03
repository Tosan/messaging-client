package com.tosan.client.messaging.creditmonitor.config;

import com.tosan.client.messaging.creditmonitor.scheduler.CreditMonitorScheduler;
import com.tosan.client.messaging.starter.service.MessagingService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CreditMonitorAutoConfigurationUTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(CreditMonitorAutoConfiguration.class))
            .withUserConfiguration(MessagingServiceTestConfig.class);

    @Configuration
    static class MessagingServiceTestConfig {
        @Bean
        MessagingService messagingService() {
            return mock(MessagingService.class);
        }
    }

    @Test
    void enabledMissing_schedulerBeanCreated() {
        contextRunner.withPropertyValues(
                        "messaging-client.credit-monitor.cron=*/10 * * * * *",
                        "messaging-client.credit-monitor.timezone=UTC",
                        "messaging-client.credit-monitor.min-credit-threshold=5000"
                )
                .run(context -> assertThat(context).hasSingleBean(CreditMonitorScheduler.class));
    }

    @Test
    void enabledTrue_schedulerBeanCreated() {
        contextRunner.withPropertyValues(
                        "messaging-client.credit-monitor.enabled=true",
                        "messaging-client.credit-monitor.cron=*/10 * * * * *",
                        "messaging-client.credit-monitor.timezone=UTC",
                        "messaging-client.credit-monitor.min-credit-threshold=5000"
                )
                .run(context -> assertThat(context).hasSingleBean(CreditMonitorScheduler.class));
    }

    @Test
    void enabledFalse_schedulerBeanNotCreated() {
        contextRunner.withPropertyValues(
                        "messaging-client.credit-monitor.enabled=false",
                        "messaging-client.credit-monitor.cron=*/10 * * * * *",
                        "messaging-client.credit-monitor.timezone=UTC",
                        "messaging-client.credit-monitor.min-credit-threshold=5000"
                )
                .run(context -> assertThat(context).doesNotHaveBean(CreditMonitorScheduler.class));
    }
}
