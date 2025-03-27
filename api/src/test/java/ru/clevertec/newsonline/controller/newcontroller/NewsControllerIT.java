package ru.clevertec.newsonline.controller.newcontroller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class NewsControllerIT {

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
    void getNews_NewsExists_ReturnNews() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/catalogue-api/news/8918718")
                .with(jwt().jwt(builder -> builder.claim("scope", "view_catalogue")));

        //when
        mockMvc.perform(requestBuilder)

                //then
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.headerNews").value("Почему все хотят попасть на Щелкунчика"),
                        jsonPath("$.articleId").value(8918718),
                        jsonPath("$.isPublished").value(true)
                );
    }

    @Test
    void getNews_NewsDoNotExist_ReturnNotFound() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/catalogue-api/news/9999999")
                .with(jwt().jwt(builder -> builder.claim("scope", "view_catalogue")));

        //when
        mockMvc.perform(requestBuilder)

                //then
                .andDo(print())
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    void getNews_NewsIsExist_UserNotAuth() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/catalogue-api/news/8918718")
                .with(jwt());

        //when
        mockMvc.perform(requestBuilder)

                //then
                .andDo(print())
                .andExpectAll(
                        status().isForbidden()
                );
    }

    @Test
    void updateNews_RequestIsValid_ReturnNoContent() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/catalogue-api/news/8918718")
                .file(new MockMultipartFile(
                        "newsDto",
                        null,
                        MediaType.APPLICATION_JSON_VALUE,
                        """
                                {
                                    "headerNews": "Тестовый заголовок",
                                    "dateOfNews": null,
                                    "articleId": 8918718,
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
                                """.getBytes()
                ))
                .file(new MockMultipartFile(
                        "image",
                        "test-image.jpg",
                        MediaType.IMAGE_JPEG_VALUE,
                        "test image content".getBytes()
                ))
                .with(jwt().jwt(builder -> builder.claim("scope", "edit_catalogue")));

        requestBuilder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PATCH");
                return request;
            }
        });

        //when
        mockMvc.perform(requestBuilder)

                //then
                .andDo(print())
                .andExpectAll(
                        status().isNoContent()
                );
    }

    @Test
    void updateNews_NewsNotExist_ReturnNoContent() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/catalogue-api/news/1111111")
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
                                """.getBytes()
                ))
                .file(new MockMultipartFile(
                        "image",
                        "test-image.jpg",
                        MediaType.IMAGE_JPEG_VALUE,
                        "test image content".getBytes()
                ))
                .with(jwt().jwt(builder -> builder.claim("scope", "edit_catalogue")));

        requestBuilder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PATCH");
                return request;
            }
        });

        //when
        mockMvc.perform(requestBuilder)

                //then
                .andDo(print())
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    void updateNews_RequestIsInvalid_ReturnError() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/catalogue-api/news/8918718")
                .file(new MockMultipartFile(
                        "newsDto",
                        null,
                        MediaType.APPLICATION_JSON_VALUE,
                        """
                                {
                                    "headerNews": "",
                                    "dateOfNews": null,
                                    "articleId": 8918718,
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
                                """.getBytes()
                ))
                .file(new MockMultipartFile(
                        "image",
                        "test-image.jpg",
                        MediaType.IMAGE_JPEG_VALUE,
                        "test image content".getBytes()
                ))
                .with(jwt().jwt(builder -> builder.claim("scope", "edit_catalogue")));

        requestBuilder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PATCH");
                return request;
            }
        });

        //when
        mockMvc.perform(requestBuilder)

                //then
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON),
                        jsonPath("$.properties.errors").exists(),
                        jsonPath("$.properties.errors").value(Matchers.containsInAnyOrder(
                                "Нет описания", "Имя новости пустое"
                        )));
    }

    @Test
    void updateNews_RequestIsForbidden_ReturnForbidden() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/catalogue-api/news/8918718")
                .file(new MockMultipartFile(
                        "newsDto",
                        null,
                        MediaType.APPLICATION_JSON_VALUE,
                        """
                                {
                                    "headerNews": "",
                                    "dateOfNews": null,
                                    "articleId": 8918718,
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
                                """.getBytes()
                ))
                .file(new MockMultipartFile(
                        "image",
                        "test-image.jpg",
                        MediaType.IMAGE_JPEG_VALUE,
                        "test image content".getBytes()
                ))
                .with(jwt().jwt(builder -> builder.claim("scope", "view_catalogue")));

        requestBuilder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PATCH");
                return request;
            }
        });

        //when
        mockMvc.perform(requestBuilder)

                //then
                .andDo(print())
                .andExpectAll(
                        status().isForbidden()
                        );
    }

    @Test
    void deleteNews_NewsIsExist_ReturnNoContent() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/catalogue-api/news/8918718")
                .with(jwt().jwt(builder -> builder.claim("scope", "edit_catalogue")));

        //when
        mockMvc.perform(requestBuilder)

        //then
                .andDo(print())
                .andExpectAll(
                        status().isNoContent()
                );
    }

    @Test
    void deleteNews_UserNotAuthorized_ReturnForbidden() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/catalogue-api/news/8918718")
                .with(jwt().jwt(builder -> builder.claim("scope", "view_catalogue")));

        //when
        mockMvc.perform(requestBuilder)

        //then
                .andDo(print())
                .andExpectAll(
                        status().isForbidden()
                );
    }

    @Test
    void deleteNews_NewsNotExist_ReturnNotFound() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/catalogue-api/news/1111111")
                .with(jwt().jwt(builder -> builder.claim("scope", "edit_catalogue")));

        //when
        mockMvc.perform(requestBuilder)

        //then
                .andDo(print())
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    void updateNewsComment_NewsIsExist_ReturnNoContent() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/catalogue-api/news/8918718/add-comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "textComment": "Новый комментарий"
                        }""")
                .with(jwt().jwt(builder -> builder.claim("scope", "view_catalogue")));

        //when
        mockMvc.perform(requestBuilder)

        //then
                .andDo(print())
                .andExpectAll(
                        status().isNoContent()
                );
    }

    @Test
    void updateNewsComment_CommentIsInvalid_ReturnNoContent() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/catalogue-api/news/8918718/add-comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "textComment": ""
                        }""")
                .with(jwt().jwt(builder -> builder.claim("scope", "view_catalogue")));

        //when
        mockMvc.perform(requestBuilder)

        //then
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON),
                        jsonPath("$.properties.errors").exists(),
                        jsonPath("$.properties.errors").value(Matchers.containsInAnyOrder(
                                "Нет содержимого комментария"
                        )));
    }

    @Test
    void updateNewsComment_NewsNotExist_ReturnNotFound() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/catalogue-api/news/1111111/add-comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "textComment": "Новый комментарий"
                        }""")
                .with(jwt().jwt(builder -> builder.claim("scope", "view_catalogue")));

        //when
        mockMvc.perform(requestBuilder)

        //then
                .andDo(print())
                .andExpectAll(
                        status().isNotFound()
                );
    }
}