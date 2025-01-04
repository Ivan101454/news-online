package ru.clevertec.newsonline.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.clevertec.newsonline.data.NewsTestBuilder;
import ru.clevertec.newsonline.entity.News;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class NewsRepositoryTest {

    @Test
    public void findNewsById() {

        final NewsRepository newsRepository;

        //given
        UUID newsId = NewsTestBuilder.builder().build().buildNews().getNewsId();
        News expectedNews = NewsTestBuilder.builder().build().buildNews();
        //when
        News actualNews = newsRepository.findById(newsId).orElseThrow();

        //then
        assertEquals(expectedNews.getHeaderNews(), actualNews.getHeaderNews());

    }

}