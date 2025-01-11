package ru.clevertec.newsonline.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
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
    @ConditionalOnProperty(value = "cache.algorithm", havingValue = "LFU")
    public Cache<UUID, News> cacheLfu() {
        return new LFUCache<>(maxSize);
    }

    @Bean
    @ConditionalOnProperty(value = "cache.algorithm", havingValue = "LRU")
    public LRUCache<UUID, News> cacheLru() {
        return new LRUCache<>(maxSize);
    }


}

