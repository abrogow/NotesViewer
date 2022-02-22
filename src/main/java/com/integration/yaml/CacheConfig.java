package com.integration.yaml;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfig {

    public static final String DEFAULT_EXPIRATION_TIME_CACHE_NAME = "defaultExpirationTimeCache";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(DEFAULT_EXPIRATION_TIME_CACHE_NAME);
    }
}
