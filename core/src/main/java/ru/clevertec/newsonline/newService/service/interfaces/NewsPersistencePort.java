package ru.clevertec.newsonline.newService.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.newsonline.newService.dto.CategoryDto;
import ru.clevertec.newsonline.newService.dto.CommentDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.dto.PictureDto;
import ru.clevertec.newsonline.newService.filter.NewsFilter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NewsPersistencePort{

    Optional<NewsDto> findByArticleId(int id);

    List<NewsDto> findByCategory(CategoryDto categoryDto, Pageable pageable);

    List<NewsDto> findAll();

    Page<NewsDto> findAll(Pageable pageable);

    NewsDto save(NewsDto newsDto);

    void deleteByArticleId(int id);

    Optional<NewsDto> update(int id, NewsDto newsDto, CategoryDto categoryDto);

    List<NewsDto> filterWord(NewsFilter newsFilter, Pageable pageable);

    Optional<NewsDto> addCommentToNewsList(int id, CommentDto commentDto);

    Optional<NewsDto> addPictureToNewsList(int id, PictureDto pictureDto);
}
