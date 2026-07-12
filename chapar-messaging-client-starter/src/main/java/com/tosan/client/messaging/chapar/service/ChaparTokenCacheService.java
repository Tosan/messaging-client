package com.tosan.client.messaging.chapar.service;

import com.tosan.client.redis.api.TedissonCacheManager;
import com.tosan.client.redis.cacheconfig.CacheConfig;

import java.util.concurrent.TimeUnit;

/**
 * @author Amirhossein Zamanzade
 * @since 5/17/26
 */
public class ChaparTokenCacheService {

    private static final String TOKEN_CACHE_NAME = "chapar_token_cache";
    private static final String TOKEN_CACHE_KEY = "chapar_token";
    private final TedissonCacheManager cacheManager;

    public ChaparTokenCacheService(TedissonCacheManager cacheManager) {
        this.cacheManager = cacheManager;
        CacheConfig cacheConfig = new CacheConfig();
        cacheConfig.setMaxSize(10);
        cacheManager.createCache(TOKEN_CACHE_NAME, cacheConfig);
    }

    public void addToken(String token, long expiresInSeconds) {
        cacheManager.addItemToCache(TOKEN_CACHE_NAME, TOKEN_CACHE_KEY, token, expiresInSeconds, TimeUnit.SECONDS);
    }

    public String getToken() {
        return cacheManager.getItemFromCache(TOKEN_CACHE_NAME, TOKEN_CACHE_KEY);
    }

    public void removeToken() {
        cacheManager.removeItemFromCache(TOKEN_CACHE_NAME, TOKEN_CACHE_KEY);
    }
}
