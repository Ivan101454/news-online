package ru.clevertec.newsonline.repository.newrepository.adapter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.clevertec.newsonline.newService.dto.CommentDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentJpaAdapterTest {

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
    private CommentJpaAdapter commentJpaAdapter;
    @Autowired
    private NewsJpaAdapter newsJpaAdapter;

    @Test
    void findAll() {
    }

    @Test
    void testFindAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
        //given
//        Optional<NewsDto> newsDto = newsJpaAdapter.findByArticleId(8918718);
//        Optional<CommentDto> commentDto = commentJpaAdapter.findById(UUID.fromString("62fb1340-d3ab-4735-bcf3-fe4de8bd1093"));

        //when
//        CommentDto save = commentJpaAdapter.save(commentDto.get());
        
        //then



    }

    @Test
    void delete() {
    }

    @Test
    void filterWord() {
    }
}