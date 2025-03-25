package ru.clevertec.newsonline.controller.newcontroller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Locale;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
class NewssControllerIT {

    @Autowired
    MockMvc mockMvc;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:13:3"
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

    @Test
    void findNews_ReturnNewsList() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/catalogue-api/news/list")
                .with(jwt().jwt(builder -> builder.claim("scope", "view_catalogue")));
        //when
        mockMvc.perform(requestBuilder)

                //then
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                );
    }

    @Test
    void findNews_ForbiddenWithoutRole() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/catalogue-api/news/list")
                .with(jwt());

        //when
        mockMvc.perform(requestBuilder)

                //then
                .andExpectAll(
                        status().isForbidden()
                );
    }


    @Test
    void createNews_ShouldReturnNewsByArticle() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/catalogue-api/news")
                .file(new MockMultipartFile(
                        "newsDto",
                        null,
                        MediaType.APPLICATION_JSON_VALUE,
                        """
                                {
                                    "headerNews": "Тестовый заголовок",
                                    "dateOfNews": null,
                                    "articleId": 1111111,
                                    "isPublished": true,
                                    "shortDescription": "Тестовое описание",
                                    "pictures": null,
                                    "comments": null
                                }
                                """.getBytes()
                ))
                .file(new MockMultipartFile(
                        "categoryDto",
                        null,
                        MediaType.APPLICATION_JSON_VALUE,
                        """
                                {
                                    "categoryId": null,
                                    "section": "PEOPLE",
                                    "newsList": null
                                }
                                """.getBytes() // JSON-данные для categoryDto
                ))
                .file(new MockMultipartFile(
                        "image",
                        "test-image.jpg",
                        MediaType.IMAGE_JPEG_VALUE,
                        "test image content".getBytes()
                ))
                .with(jwt().jwt(builder -> builder.claim("scope", "edit_catalogue")));

        //when
        mockMvc.perform(requestBuilder)

                //then
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        header().string(HttpHeaders.LOCATION, "http://localhost/catalogue-api/news/1111111"),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.headerNews").value("Тестовый заголовок"),
                        jsonPath("$.articleId").value(1111111),
                        jsonPath("$.isPublished").value(true)
                );
    }

    @Test
    void createNews_RequestIsInvalid_ShouldReturnProblemDetail() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/catalogue-api/news")
                .file(new MockMultipartFile(
                        "newsDto",
                        null,
                        MediaType.APPLICATION_JSON_VALUE,
                        """
                                {
                                    "headerNews": " ",
                                    "dateOfNews": null,
                                    "articleId": 3,
                                    "isPublished": true,
                                    "shortDescription": "",
                                    "pictures": null,
                                    "comments": null
                                }
                                """.getBytes()
                ))
                .file(new MockMultipartFile(
                        "categoryDto",
                        null,
                        MediaType.APPLICATION_JSON_VALUE,
                        """
                                {
                                    "categoryId": null,
                                    "section": "PEOPLE",
                                    "newsList": null
                                }
                                """.getBytes() // JSON-данные для categoryDto
                ))
                .file(new MockMultipartFile(
                        "image",
                        "test-image.jpg",
                        MediaType.IMAGE_JPEG_VALUE,
                        "test image content".getBytes()
                ))
                .locale(Locale.of("ru", "RU"))
                .with(jwt().jwt(builder -> builder.claim("scope", "edit_catalogue")));

        //when
        mockMvc.perform(requestBuilder)

                //then
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON),
                        jsonPath("$.properties.errors").exists(),
                        jsonPath("$.properties.errors").value(Matchers.containsInAnyOrder(
                                "Нет описания",
                                "Артикул должен быть 6 цифр"
                        )));
    }

    @Test
    void createNews_NotAuthorized_ReturnForbidden() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/catalogue-api/news")
                .file(new MockMultipartFile(
                        "newsDto",
                        null,
                        MediaType.APPLICATION_JSON_VALUE,
                        """
                                {
                                    "headerNews": " ",
                                    "dateOfNews": null,
                                    "articleId": 3,
                                    "isPublished": true,
                                    "shortDescription": "",
                                    "pictures": null,
                                    "comments": null
                                }
                                """.getBytes()
                ))
                .file(new MockMultipartFile(
                        "categoryDto",
                        null,
                        MediaType.APPLICATION_JSON_VALUE,
                        """
                                {
                                    "categoryId": null,
                                    "section": "PEOPLE",
                                    "newsList": null
                                }
                                """.getBytes() // JSON-данные для categoryDto
                ))
                .file(new MockMultipartFile(
                        "image",
                        "test-image.jpg",
                        MediaType.IMAGE_JPEG_VALUE,
                        "test image content".getBytes()
                ))
                .locale(Locale.of("ru", "RU"))
                .with(jwt().jwt(builder -> builder.claim("scope", "view_catalogue")));

        //when
        mockMvc.perform(requestBuilder)

                //then
                .andDo(print())
                .andExpectAll(
                        status().isForbidden()
                        );
    }
}