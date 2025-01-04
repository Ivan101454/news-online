package ru.clevertec.newsonline.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.newsonline.data.NewsTestBuilder;
import ru.clevertec.newsonline.dto.NewsDto;
import ru.clevertec.newsonline.entity.Comment;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.filter.CommentFilter;
import ru.clevertec.newsonline.filter.NewsFilter;
import ru.clevertec.newsonline.mapper.NewsMapper;
import ru.clevertec.newsonline.service.CommentService;
import ru.clevertec.newsonline.service.NewsService;

import javax.swing.text.html.Option;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class NewsControllerTest {

    @Test
    void findNewsById() {
//        //given
//        UUID id = UUID.randomUUID();
//        //when
//        doReturn(Optional.of(new NewsDto()))
//                .when(newsService).findById(id);
//        var actualResult = newsService.findById(id);
//        //then
//        var expectedResult =
//        assertEquals();

//
//
//
    }
}