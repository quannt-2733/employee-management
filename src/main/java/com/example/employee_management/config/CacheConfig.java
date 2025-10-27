package com.example.employee_management.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {
    private final long cacheMaxSize;
    private final long cacheExpireMinutes;

    public CacheConfig(
            @Value("${app.caching.employee-count.max-size}") long cacheMaxSize,
            @Value("${app.caching.employee-count.expire-minutes}") long cacheExpireMinutes) {
        this.cacheMaxSize = cacheMaxSize;
        this.cacheExpireMinutes = cacheExpireMinutes;
    }

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("employeeCount");

        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(cacheExpireMinutes, TimeUnit.MINUTES)
                .maximumSize(cacheMaxSize));

        return cacheManager;
    }
}
