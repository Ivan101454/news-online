package ru.clevertec.newsonline.controller.newcontroller;

import data.UtilNews;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NewssControllerIT {

    @Autowired
    MockMvc mockMvc;

//    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
//            "postgres:16-alpine"
//    );
//
//    @BeforeAll
//    static void beforeAll() {
//        postgres.start();
//    }
//
//    @AfterAll
//    static void afterAll() {
//        postgres.stop();
//    }
//
//    @DynamicPropertySource
//    static void configureProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgres::getJdbcUrl);
//        registry.add("spring.datasource.username", postgres::getUsername);
//        registry.add("spring.datasource.password", postgres::getPassword);
//    }
//
//    @Autowired
//    private NewsController newsController;

    @Test
    void findNews_ReturnNewsList() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("catalogue-api/news/list")
                .with(jwt().jwt(builder -> builder.claim("scope", "view_catalogue")));
        String json = UtilNews.writeNewsAsJsonString();
        //when
        mockMvc.perform(requestBuilder)

        //then
        .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                [j, j]
                                """.formatted(json)
                        )
                );
    }

    @Test
    void findNewsWithPagination() {
    }

    @Test
    void findNewsByFilter() {
    }

    @Test
    void createNews() {
    }
}