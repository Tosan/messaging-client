package com.tosan.client.messaging.creditmonitor.scheduler;

import com.tosan.client.messaging.creditmonitor.config.CreditMonitorProperties;
import com.tosan.client.messaging.starter.model.AccountInfoResponse;
import com.tosan.client.messaging.starter.service.MessagingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@RequiredArgsConstructor
public class CreditMonitorScheduler {
    private final MessagingService messagingService;
    private final CreditMonitorProperties properties;

    @Scheduled(cron = "${messaging-client.credit-monitor.cron}",
            zone = "${messaging-client.credit-monitor.timezone}")
    public void checkAccountCredit() {
        try {
            AccountInfoResponse accountInfo = messagingService.getAccountInfo();
            Long remainCredit = accountInfo.getRemainCreditRial();
            Long minCreditThreshold = properties.getMinimumCreditThresholdRial();
            if (remainCredit == null) {
                log.error("Messaging account credit is null, cannot evaluate threshold={}",
                        minCreditThreshold);
                return;
            }
            if (minCreditThreshold != null && remainCredit < minCreditThreshold) {
                log.warn("Messaging account credit alert: credit={} is below configured threshold={}",
                        remainCredit, minCreditThreshold);
            }
            // TODO: maybe we need this
//             else {
//                log.info("Messaging account credit check passed, current credit={}, configured threshold={}",
//                        remainCredit, minCreditThreshold);
//            }
        } catch (Exception e) {
            log.error("Messaging account credit check failed: ", e);
        }
    }
}
