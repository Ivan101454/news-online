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
import ru.clevertec.newsonline.mapper.NewsMapper;

import java.util.List;
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
    private NewsMapper newsMapper;

    @SneakyThrows
    @Test
    public void shouldReturnDtoResponseById() {
        //given
        NewsTestBuilder nb = NewsTestBuilder.builder().build();
        News news = nb.buildNews();
        UUID newsId = news.getNewsId();
        String jsonFromNewsDto = nb.getJsonFromNewsDto(objectMapper, newsMapper);

        //when
        ResponseEntity<NewsDto> newsById = newsController.findNewsById(newsId);


        //then
        assertEquals(HttpStatus.OK, newsById.getStatusCode());

        assertEquals(news.getHeaderNews(), newsById.getBody().headerNews());
    }

    @Test
    public void shouldReturnListNews() {
        //given
        NewsTestBuilder nb = NewsTestBuilder.builder().build();
        News newsExpect = nb.buildFifthNews();

        //when
        ResponseEntity<List<NewsDto>> allNewsActual = newsController.findAllNews(1, 5);


        //then
        assertEquals(newsExpect.getHeaderNews(), allNewsActual.getBody().getLast().headerNews());

    }

    @Test
    public void shouldCreateNewNews() {
        //given
        NewsTestBuilder nb = NewsTestBuilder.builder().build();
        NewsDto newsDto = nb.builNewNewsDto(NewsMapper.INSTANCE);

        //when
        ResponseEntity<NewsDto> newsDtoResponseEntity = newsController.create(newsDto);
        String expectHeader = newsDtoResponseEntity.getBody().headerNews();


        //then
        assertEquals(newsDto.headerNews(), expectHeader);

    }

    @Test
    public void shouldUpdateNews() {
        //given
        NewsTestBuilder nb = NewsTestBuilder.builder().build();
        News news = nb.buildNews();
        UUID newsId = news.getNewsId();
        NewsDto newsDto = nb.builNewNewsDto(NewsMapper.INSTANCE);

        //when
        newsController.update(newsId, newsDto);
        ResponseEntity<NewsDto> expectResponse = newsController.findNewsById(newsId);
        //then
        assertEquals(newsDto.headerNews(), expectResponse.getBody().headerNews());

    }

    @Test
    public void shouldDeleteNews() {
        //given
        NewsTestBuilder nb = NewsTestBuilder.builder().build();
        News news = nb.buildNews();
        UUID newsId = news.getNewsId();

        //when
        ResponseEntity<Void> delete = newsController.delete(newsId);

        //then
        assertEquals(HttpStatus.NO_CONTENT, delete.getStatusCode());
    }




}
