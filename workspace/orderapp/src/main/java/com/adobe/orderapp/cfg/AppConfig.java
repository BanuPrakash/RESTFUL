package com.adobe.orderapp.cfg;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching
@EnableScheduling
@RequiredArgsConstructor
public class AppConfig {
    private final CacheManager cacheManager;

//    @Scheduled(fixedRate = 1000)
    // every 30 min
    @Scheduled(cron = "0 0/30 * * * *")
    public void clearCache() {
        System.out.println("Cache Cleared");
        cacheManager.getCacheNames().forEach(cache -> {
            cacheManager.getCache(cache).clear();
        });
    }
}
