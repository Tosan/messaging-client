package com.tosan.client.messaging.sample.config;

import com.tosan.client.messaging.chapar.service.ChaparTokenCacheService;
import com.tosan.client.redis.api.TedissonCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageClientSampleConfig {

    @Bean("chapar-tokenCacheService")
    public ChaparTokenCacheService chaparTokenCacheService(TedissonCacheManager cacheManager) {
        return new ChaparTokenCacheService(cacheManager);
    }
}
