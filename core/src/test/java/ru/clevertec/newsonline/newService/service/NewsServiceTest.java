package ru.clevertec.newsonline.newService.service;

import Data.CreateData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.newsonline.exception.NotFoundException;
import ru.clevertec.newsonline.newService.dto.CategoryDto;
import ru.clevertec.newsonline.newService.dto.CommentDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.dto.PictureDto;
import ru.clevertec.newsonline.newService.filter.NewsFilter;
import ru.clevertec.newsonline.newService.service.interfaces.NewsPersistencePort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock
    private NewsPersistencePort newsPersistencePort;
    @InjectMocks
    private NewsService newsService;

    @Test
    void findByArticleId_ShouldReturnNewsDtoByArticle() {
        //given
        NewsDto newsDtoExpect = CreateData.createNewsDto();
        doReturn(Optional.of(newsDtoExpect))
                .when(newsPersistencePort).findByArticleId(1234567);

        //when
        Optional<NewsDto> newsDtoResult = newsService.findByArticleId(1234567);

        //then
        assertEquals(newsDtoExpect.articleId(), newsDtoResult.get().articleId());
    }

    @Test
    void findAll_ShouldReturnListNewsDto() {
        //given
        List<NewsDto> listExpect = CreateData.createListNewsDto();
        doReturn(listExpect)
                .when(newsPersistencePort).findAll();
        //when
        List<NewsDto> listResult = newsService.findAll();

        //then
        assertEquals(listExpect.get(10).articleId(), listResult.get(10).articleId());
    }

    @Test
    void findByPage_ShouldReturnListWithLimitAndOffset() {
        //given
        List<NewsDto> list = CreateData.createListNewsDto();
        List<NewsDto> listExpect = list.stream().skip(50).limit(10).toList();
        Pageable pageable = PageRequest.of(5, 10);
        PageImpl<NewsDto> page = new PageImpl<>(listExpect, pageable, listExpect.size());
        doReturn(page)
                .when(newsPersistencePort).findAll(pageable);
        //when
        List<NewsDto> listResult = newsService.findByPage(6, 10);

        //then
        assertEquals(listExpect.getFirst().articleId(), listResult.getFirst().articleId());

    }

    @Test
    void create_ShouldCreateNews() {
        //given
        NewsDto newsDto = CreateData.createNewsDto();
        doReturn(newsDto)
                .when(newsPersistencePort).save(newsDto);
        //when
        Optional<NewsDto> newsDtoResult = newsService.create(newsDto);

        //then
        assertEquals(newsDto.articleId(), newsDtoResult.get().articleId());

    }

    @Test
    void update_ShouldThrownExceptionIfEntityForUpdateNotFound() {
        //given
        NewsDto newsDto = CreateData.createNewsDto();
        CategoryDto categoryDto = CreateData.createCategoryDto();
        doThrow(new NotFoundException("Сущность не найдена по id"))
                .when(newsPersistencePort).findByArticleId(1234567);
        //when

        //then
        assertThrows(NotFoundException.class, () -> {
            newsService.update(1234567, newsDto, categoryDto);
        });

    }

    @Test
    void delete_ShouldThrowExceptionIfEntityForDeleteNotFoundAlready() {
        //given
        NewsDto newsDto = CreateData.createNewsDto();
        CategoryDto categoryDto = CreateData.createCategoryDto();
        doThrow(new NotFoundException("Сущность не найдена по id"))
                .when(newsPersistencePort).findByArticleId(1234567);
        //when

        //then
        assertThrows(NotFoundException.class, () -> {
            newsService.delete(1234567);
        });
    }

    @Test
    void findEntityByFilter_ShouldReturnListDtoByFilter() {
        //given
        NewsDto newsDto = CreateData.createNewsDto();
        Pageable pageable = PageRequest.of(0, 10);
        doReturn(List.of(newsDto))
                .when(newsPersistencePort).filterWord(new NewsFilter("коттедж", null), pageable);
        //when
        List<NewsDto> filter = newsService.findEntityByFilter(new NewsFilter("коттедж", null), 1, 10);
        //then
        assertEquals(newsDto.articleId(), filter.getFirst().articleId());

    }

    @Test
    void addComment_ShouldAddCommentForNews() {
        //given
        CommentDto commentDto = CreateData.createCommentDto();
        NewsDto newsDto = CreateData.createNewsDto();
        doReturn(Optional.of(newsDto))
                .when(newsPersistencePort).addCommentToNewsList(1234567, commentDto);
        //when
        Optional<NewsDto> newsDtoResult = newsPersistencePort.addCommentToNewsList(1234567, commentDto);

        //then
        assertEquals(newsDto.articleId(), newsDtoResult.get().articleId());

    }

    @Test
    void addPicture_ShouldAddPictureForNews() {
        //given
        PictureDto pictureDto = CreateData.createPictureDto();
        NewsDto newsDto = CreateData.createNewsDto();
        doReturn(Optional.of(newsDto))
                .when(newsPersistencePort).addPictureToNewsList(1234567, pictureDto);
        //when
        Optional<NewsDto> newsDtoResult = newsPersistencePort.addPictureToNewsList(1234567, pictureDto);

        //then
        assertEquals(newsDto.articleId(), newsDtoResult.get().articleId());
        verify(newsPersistencePort).addPictureToNewsList(1234567, pictureDto);
    }
}