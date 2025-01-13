package ru.clevertec.newsonline.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.newsonline.data.NewsTestBuilder;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.exception.NotFoundException;
import ru.clevertec.newsonline.filter.NewsFilter;
import ru.clevertec.newsonline.serviceinteface.IFilterRepository;
import ru.clevertec.newsonline.serviceinteface.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class NewsServiceTest {

    @Mock
    private IRepository<News> newsRepository;
    @Mock
    private IFilterRepository<News, NewsFilter> iFilterRepository;
    @InjectMocks
    private NewsService<News, NewsFilter> newsService;

    @Test
    void shouldFindNewsById() {
        //given
        News expectNews = NewsTestBuilder.builder().build().buildNews();
        UUID newsId = expectNews.getNewsId();
        doReturn(Optional.of(expectNews))
                .when(newsRepository).findById(newsId);
        //when
        Optional<News> actualNews = newsService.findById(newsId);
        //then
        assertEquals(expectNews, actualNews.orElseThrow());
    }

    @Test
    void shouldFindAllNews() {
        //given
        final int quantity = 20;
        News news = NewsTestBuilder.builder().build().buildNews();
        List<News> list = new ArrayList<>();
        for (int i=0; i<quantity; i++) {
            list.add(news);
        }
        doReturn(list)
                .when(newsRepository).findAll();
        //when
        List<News> all = newsService.findAll();

        //then
        assertEquals(quantity, all.size());
    }

    @Test
    void shouldFindNewsWithPagination() {
        //given
        final int page = 2;
        final int size = 5;
        News news = NewsTestBuilder.builder().build().buildNews();
        List<News> list = new ArrayList<>();
        for (int i=0; i<size; i++) {
            list.add(news);
        }

        Pageable pageable = PageRequest.of(2, 5);
        Page<News> pageList = new PageImpl<News>(list, pageable, list.size());
        doReturn(pageList)
                .when(newsRepository).findAll(pageable);

        //when
        List<News> byPage = newsService.findByPage(2, 5);

        //then
        assertEquals(size, byPage.size());
    }

    @Test
    void shouldSaveEntity() {
        //given
        News news = NewsTestBuilder.builder().build().buildNews();
        doReturn(news)
                .when(newsRepository).save(news);
        //when
        Optional<News> actualNews = newsService.create(news);

        //then
        assertEquals(news, actualNews.orElseThrow());
    }

    @Test
    void shouldUpdateEntity() {
        //given
        News news = NewsTestBuilder.builder().build().buildNews();
        News updateNews = NewsTestBuilder.builder().build().buildNews();
        updateNews.setHeaderNews("Update");
        UUID newsId = news.getNewsId();
        doReturn(Optional.of(news))
                .when(newsRepository).findById(newsId);
        doReturn(updateNews)
                .when(newsRepository).save(updateNews);
        //when
        Optional<News> updateActual = newsService.update(newsId, updateNews);

        //then
        assertEquals(updateNews.getHeaderNews(), updateActual.orElseThrow().getHeaderNews());
    }

    @Test
    void shouldThrowIfDeleteEntityNotFind() {
        //given
        News news = NewsTestBuilder.builder().build().buildNews();
        UUID newsId = news.getNewsId();
        doReturn(Optional.empty())
                .when(newsRepository).findById(newsId);
        //when

        //then
        assertThrows(NotFoundException.class, () -> newsService.delete(newsId));

    }

    @Test
    void shouldFindEntityBuFilter() {
        //given
        final int page = 1;
        final int size = 5;
        NewsTestBuilder nb = NewsTestBuilder.builder().build();
        NewsFilter newsFilter = nb.buildNewsFilter();
        News news = nb.buildNews();
        List<News> list = List.of(news);
        doReturn(list)
                .when(iFilterRepository).filterWord(newsFilter, News.class, page, size);
        //when
        List<News> entityByFilter = newsService.findEntityByFilter(newsFilter, News.class, page, size);

        //then
        assertEquals(news, entityByFilter.getFirst());
    }
}
