package ru.clevertec.newsonline.newService.service.interfaces;

import ru.clevertec.newsonline.newService.dto.NewsDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NewsServicePort {
    Optional<NewsDto> findById(UUID id);

    List<NewsDto> findAll();

    List<NewsDto> findByPage(int pageNumber, int pageSize);

    Optional<NewsDto> create(NewsDto newsDto);

    void update(UUID id, NewsDto newsDto);

    void delete(UUID id);

//    List<NewsDto> findEntityByFilter(F f, Class<NewsDto> entityClazz, int pageNumber, int pageSize);
}
