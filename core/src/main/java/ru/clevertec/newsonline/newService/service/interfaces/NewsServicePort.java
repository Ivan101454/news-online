package ru.clevertec.newsonline.newService.service.interfaces;

import ru.clevertec.newsonline.newService.dto.CommentDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.dto.PictureDto;
import ru.clevertec.newsonline.newService.filter.NewsFilter;

import java.util.List;
import java.util.Optional;

public interface NewsServicePort {

    Optional<NewsDto> findByArticleId(int articleId);

    List<NewsDto> findAll();

    List<NewsDto> findByPage(int pageNumber, int pageSize);

    Optional<NewsDto> create(NewsDto newsDto);

    void update(int articleId, NewsDto newsDto);

    void delete(int articleId);

    List<NewsDto> findEntityByFilter(NewsFilter filter, int pageNumber, int pageSize);

    void addComment(int articleId, CommentDto commentDto);
    void addPicture(int articleId, PictureDto pictureDto);
}
