package ru.clevertec.newsonline.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ActiveProfiles;
import ru.clevertec.newsonline.data.NewsTestBuilder;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.filter.NewsFilter;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NewsRepositoryIT {

    @Autowired
    private NewsRepository newsRepository;

    @Test
    public void findNewsById() {

        //given
        UUID newsId = NewsTestBuilder.builder().build().buildNews().getNewsId();
        News expectedNews = NewsTestBuilder.builder().build().buildNews();

        //when
        News actualNews = ((CrudRepository<News, UUID>) newsRepository).findById(newsId).orElseThrow();

        //then
        assertEquals(expectedNews.getHeaderNews(), actualNews.getHeaderNews());

    }

    @Test
    public void findAllWithPagination() {
        //given
        Pageable pageRequest = PageRequest.of(0, 5);
        NewsTestBuilder nb = NewsTestBuilder.builder().build();
        News news = nb.buildNews();
        News news5 = nb.buildFifthNews();

        //when
        List<News> actualNewsList = newsRepository.findAll(pageRequest).getContent();

        //then
        assertFalse(actualNewsList.isEmpty());
        assertEquals(5, actualNewsList.size());
        assertEquals(news.getNewsId(), actualNewsList.getFirst().getNewsId());
    }

    @Test
    public void findAllNews() {
        //given
        News news = NewsTestBuilder.builder().build().buildNews();
        //when
        List<News> allActualNews = newsRepository.findAll();
        //then
        assertFalse(allActualNews.isEmpty());
        assertEquals(20, allActualNews.size());
        assertEquals(news.getNewsId(), allActualNews.getFirst().getNewsId());
    }

    @Test
    public void shouldSaveNews() {
        //given
        News newNews = NewsTestBuilder.builder().build().buildNewsForSave();
        newsRepository.flush();
        //when
        newsRepository.save(newNews);
        //then
        assertEquals(21, newsRepository.findAll().size());
//        assertTrue(((CrudRepository<News, UUID>) newsRepository).findById(newNews.getNewsId()).isPresent());
    }

    @Test
    public void shouldDeleteNews() {
        //given
        News news = NewsTestBuilder.builder().build().buildNews();

        //when
        newsRepository.delete(news);

        //then
        assertEquals(19, newsRepository.findAll().size());
        assertFalse(((CrudRepository<News, UUID>) newsRepository).findById(news.getNewsId()).isPresent());
    }

    @Test
    public void findNewsByKeyWord() {
        //given
        NewsTestBuilder nb = NewsTestBuilder.builder().build();
        NewsFilter newsFilter = nb.buildNewsFilter();
        News news = nb.buildNews();
        //when
        List<News> listOfNews = newsRepository.filterWord(newsFilter, News.class, 0, 5);

        //then
        assertEquals(1, listOfNews.size());
        assertEquals(news.getNewsId(), listOfNews.getFirst().getNewsId());
    }

}