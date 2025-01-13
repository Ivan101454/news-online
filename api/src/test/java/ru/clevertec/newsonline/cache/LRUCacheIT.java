package ru.clevertec.newsonline.cache;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.clevertec.newsonline.data.NewsTestBuilder;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.exception.NotFoundException;
import ru.clevertec.newsonline.repository.NewsRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = {
//        "cache.algorithm=LRU",
        "cache.max-size=5"
})
@ActiveProfiles({"test", "LRU"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LRUCacheIT {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private Cache<UUID, News> cache;

    @Test
    void shouldGetEntityFromCacheOrRepository() {
        //given
        UUID newsId = NewsTestBuilder.builder().build().buildNews().getNewsId();
        News expectedNews = NewsTestBuilder.builder().build().buildNews();

        //when
        News newsActual = cache.get(newsId);
        if (newsActual == null) {
            Optional<News> newsFromRepo = ((CrudRepository<News, UUID>) newsRepository).findById(newsId);
            newsFromRepo.ifPresent(x -> cache.put(newsId, x));
            newsActual = newsFromRepo.orElseThrow();
        }
        //then
        assertEquals(expectedNews.getNewsId(), newsActual.getNewsId());
    }
    @Test
    void shouldGetEntityFromCacheOnly() {
        //given
        UUID newsId = NewsTestBuilder.builder().build().buildNews().getNewsId();
        News expectedNews = NewsTestBuilder.builder().build().buildNews();
        cache.put(newsId, expectedNews);

        //when
        News newsCache = cache.get(newsId);

        //then
        assertEquals(expectedNews.getNewsId(), Optional.ofNullable(newsCache).orElseThrow().getNewsId());
    }

    @Test
    void put() {
        //given
        UUID newsId = NewsTestBuilder.builder().build().buildNews().getNewsId();
        News expectedNews = NewsTestBuilder.builder().build().buildNews();

        //when
        News putNews = cache.put(newsId, expectedNews);

        //then
        assertEquals(expectedNews.getNewsId(), putNews.getNewsId());

    }

    @Test
    void remove() {
        //given
        UUID newsId = NewsTestBuilder.builder().build().buildNews().getNewsId();
        News expectedNews = NewsTestBuilder.builder().build().buildNews();
        cache.put(newsId, expectedNews);

        //when
        cache.remove(newsId);

        //then
        assertThrows(NotFoundException.class, () -> Optional.ofNullable(cache.get(newsId)).orElseThrow(() -> new NotFoundException("test")));

    }
}