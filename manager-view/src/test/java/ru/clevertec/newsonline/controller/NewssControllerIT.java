package ru.clevertec.newsonline.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.matching.ContentPattern;
import com.github.tomakehurst.wiremock.matching.MultipartValuePattern;
import data.UtilNews;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.enums.Section;


import java.util.Arrays;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WireMockTest(httpPort = 54321)
public class NewssControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getNewNewsPage_ReturnNewsPage() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/manager-api/news/create")
                .with(user("januszek").roles("AUTHOR"));

        //when
        mockMvc.perform(requestBuilder)

                //then
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        view().name("catalogue/news/create")
                );
    }

    @Test
    void getNewNewsPage_UserNotAuth_ReturnForbidden() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/manager-api/news/create")
                .with(user("januszek"));

        //when
        mockMvc.perform(requestBuilder)

        //then
                .andDo(print())
                .andExpectAll(
                        status().isForbidden()
                );

    }

    @Test
    void getNewsList_ReturnPageWithNewsList() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/manager-api/news/list")
                .param("headerNews", " ")
                .param("shortDescription", " ")
                .param("pageNumber", "1")
                .param("pageSize", "10")
                .with(user("januszek").roles("AUTHOR"));

        WireMock.stubFor(WireMock.get(WireMock.urlPathMatching("/catalogue-api/news/list-by-filter"))
                .withQueryParam("pageNumber", WireMock.equalTo("1"))
                .withQueryParam("pageSize", WireMock.equalTo("10"))
                .withQueryParam("headerNews", WireMock.equalTo(" "))
                .withQueryParam("shortDescription", WireMock.equalTo(" "))
                .willReturn(WireMock.ok(UtilNews.writeListOfJsonNews())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        //when
        mockMvc.perform(requestBuilder)

                //then
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        view().name("catalogue/news/list"),
                        model().attribute("page", 1),
                        model().attribute("list", List.of(UtilNews.createNews(), UtilNews.createNews())));

        WireMock.verify(WireMock.getRequestedFor(WireMock.urlPathMatching("/catalogue-api/news/list-by-filter")
                ).withQueryParam("pageNumber", WireMock.equalTo("1"))
                .withQueryParam("pageSize", WireMock.equalTo("10"))
                .withQueryParam("headerNews", WireMock.equalTo(" "))
                .withQueryParam("shortDescription", WireMock.equalTo(" ")));
    }

    @Test
    void getNewsList_NotReturnPageWithNewsList_ButNotAuthUser() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/manager-api/news/list")
                .param("headerNews", " ")
                .param("shortDescription", " ")
                .param("pageNumber", "1")
                .param("pageSize", "10")
                .with(user("januszek"));


        //when
        mockMvc.perform(requestBuilder)

                //then
                .andDo(print())
                .andExpectAll(
                        status().isForbidden()
                );
    }

    @Test
    void createNews_ReturnListOfDefineNewsByArticle() throws Exception {
        //given
        NewsDto news = UtilNews.createNews();
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("newsDto", news);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/manager-api/news/create")
                .file("image", UtilNews.getFile().getBytes())
                .param("articleId", "5354289") // Параметры, соответствующие полям NewsDto
                .param("headerNews", "Это боль, больше никогда! Провел неделю с «андроидом» после 12 лет на «айфоне»") // Заголовок
                .param("shortDescription", "Что будет, если пользователь «айфонов» с 12-летним стажем перейдет на «андроид»? Спойлер — ничего хорошего. Таким подопытным стал автор этого материала. Я на время сменил свой уже несвежий iPhone 12 на актуальный Google Pixel 9 и получил лишь многократное повышение температуры в области чуть пониже спины. ") // Краткое описание
                .param("isPublished", "false")
                .param("section", Section.PEOPLE.toString())
                .with(user("januszek").roles("AUTHOR"))
                .with(csrf());


        WireMock.stubFor(WireMock.post(WireMock.urlPathMatching("/catalogue-api/news"))
                .withHeader("Content-Type", WireMock.containing("multipart/form-data"))
                .withMultipartRequestBody(WireMock.aMultipart()
                        .withName("newsDto")
                        .withBody(WireMock.equalToJson(UtilNews.writeNewsAsJsonString(), true, true)))
                .withMultipartRequestBody(WireMock.aMultipart()
                        .withName("categoryDto")
                        .withBody(WireMock.equalToJson(UtilNews.writeCategorieAsJsonString(), true, true)))
                .withMultipartRequestBody(WireMock.aMultipart()
                        .withName("image"))
                .willReturn(WireMock.okJson(UtilNews.writeNewsAsJsonString())));

        //when
        mockMvc.perform(requestBuilder)
                //then
                .andDo(print())
                .andExpectAll(
                        status().is3xxRedirection(),
                        header().string(HttpHeaders.LOCATION, "/manager-api/news/5354289")
                );

        WireMock.verify(WireMock.postRequestedFor(WireMock.urlPathMatching("/catalogue-api/news"))
                .withRequestBody(WireMock.matching(".*newsDto.*"))
        );


    }


}
