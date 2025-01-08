package ru.clevertec.newsonline.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import ru.clevertec.newsonline.cache.Cache;
import ru.clevertec.newsonline.entity.News;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "cache.algorithm=LFU", "cache.max-size=5"
})
class CacheConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void cacheLfu() {
        Cache<UUID, News> cache = (Cache<UUID, News>) context.getBean("cacheLfu");
        assertNotNull(cache);
    }

    @Test
    void cacheLru() {
        Cache<UUID, News> cache = (Cache<UUID, News>) context.getBean("cacheLru");
        assertNotNull(cache);
    }
}