package ru.clevertec.newsonline.newService.service.interfaces;

import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.filter.NewsFilter;

import java.util.List;
import java.util.Optional;

public interface NewsServicePort {

    Optional<NewsDto> findByArticleId(int article);

    List<NewsDto> findAll();

    List<NewsDto> findByPage(int pageNumber, int pageSize);

    Optional<NewsDto> create(NewsDto newsDto);

    void update(int id, NewsDto newsDto);

    void delete(int id);

    List<NewsDto> findEntityByFilter(NewsFilter filter, int pageNumber, int pageSize);
}
