package ru.clevertec.newsonline.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.clevertec.newsonline.data.NewsTestBuilder;
import ru.clevertec.newsonline.dto.NewsDto;
import ru.clevertec.newsonline.entity.News;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NewsControllerIT {

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
    private NewsController newsController;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private NewsTestBuilder nb;

    @SneakyThrows
    @Test
    @Transactional
    public void shouldReturnDtoResponseById() {
        //given
        News news = nb.buildNews();
        UUID newsId = news.getNewsId();
        String jsonFromNewsDto = nb.getJsonFromNewsDto();

        //when
        ResponseEntity<NewsDto> newsById = newsController.findNewsById(newsId);

        //then
        assertEquals(HttpStatus.OK, newsById.getStatusCode());
        assertEquals(jsonFromNewsDto, objectMapper.writeValueAsString(newsById.getBody()));

    }


}
