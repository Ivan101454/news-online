package ru.clevertec.newsonline.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import data.UtilNews;
import org.junit.jupiter.api.Test;
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
import ru.clevertec.newsonline.newService.dto.NewsDto;


import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    @WithMockUser(username = "januszek", roles = "AUTHOR")
    void createNews_ReturnListOfDefineNewsByArticle() throws Exception {
        //given
        NewsDto news = UtilNews.createNews();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/manager-api/news/create")
                .file(UtilNews.getFile())
                .param("newsDto", UtilNews.writeNewsAsJsonString())
                .param("categoryDto", UtilNews.writeCategorieAsJsonString());

        //when
        mockMvc.perform(requestBuilder)

                //then
                .andDo(print())
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("redirect:/manager-api/news/%d".formatted(news.articleId()))
                );
    }


}
