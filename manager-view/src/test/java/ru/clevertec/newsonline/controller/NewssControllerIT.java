package ru.clevertec.newsonline.controller;

import data.UtilNews;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.clevertec.newsonline.newService.dto.NewsDto;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
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

        //when
        mockMvc.perform(requestBuilder)

                //then
                .andDo(print());
//                .andExpectAll(
//                        status().isOk(),
//                        view().name("catalogue/news/list")
//                );
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
