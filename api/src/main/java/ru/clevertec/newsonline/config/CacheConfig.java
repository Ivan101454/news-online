package ru.clevertec.newsonline.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.clevertec.newsonline.cache.Cache;
import ru.clevertec.newsonline.cache.LFUCache;
import ru.clevertec.newsonline.cache.LRUCache;
import ru.clevertec.newsonline.entity.News;

import java.util.UUID;

@Configuration
public class CacheConfig {

    @Value("${cache.max-size}")
    private int maxSize;

    @Bean
//    @ConditionalOnProperty(value = "cache.algorithm", havingValue = "LFU")
    @Profile("LFU")
    public Cache<UUID, News> cacheLfu() {
        return new LFUCache<>(maxSize);
    }

    @Bean
//    @ConditionalOnProperty(value = "cache.algorithm", havingValue = "LRU")
    @Profile("LRU")
    public LRUCache<UUID, News> cacheLru() {
        return new LRUCache<>(maxSize);
    }


}

