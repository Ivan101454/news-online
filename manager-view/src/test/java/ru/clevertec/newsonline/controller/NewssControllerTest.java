package ru.clevertec.newsonline.controller;

import data.UtilNews;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import ru.clevertec.newsonline.client.BadRequestException;
import ru.clevertec.newsonline.client.NewsRestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
@DisplayName("Модульные тесты NewssController")
class NewssControllerTest {

    @Mock
    NewsRestClient newsRestClient;

    @InjectMocks
    NewssController newssController;


    @Test
    @DisplayName("createNews создаст новость и перенаправит на страницу товара")
    void createNews_RequestIsValid_ReturnRedirectionToNewsPage() {
        //given
        var news = UtilNews.createNews();
        var concurrentModel = new ConcurrentModel();

        doReturn(news)
                .when(newsRestClient)
                .createNews(news, null, null);
        //when
        var result = newssController.createNews(news, null, null, concurrentModel);

        //then
        assertEquals("redirect:/manager-api/news/5354289", result);

        verify(newsRestClient).createNews(news, null, null);
        verifyNoMoreInteractions(newsRestClient);
    }

    @DisplayName("createNews вернет страницу с ошибками, если запрос не валиден")
    @Test
    void createNews_RequestIsInvalid_ReturnPageWithErrors() {
        //given
        var news = UtilNews.createNotValidNews();
        var concurrentModel = new ConcurrentModel();

        doThrow(new BadRequestException(List.of("Ошибка 1", "Ошибка 2")))
                .when(newsRestClient)
                .createNews(news, null, null);
        //when
        var result = newssController.createNews(news, null, null, concurrentModel);

        //then
        assertEquals("catalogue/news/create", result);
        assertEquals(news, concurrentModel.getAttribute("news"));
        assertEquals(List.of("Ошибка 1", "Ошибка 2"), concurrentModel.getAttribute("errors"));

        verify(newsRestClient).createNews(news, null, null);
        verifyNoMoreInteractions(newsRestClient);
    }
}