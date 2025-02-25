package ru.clevertec.newsonline.newService.service.interfaces;

import org.springframework.data.domain.Pageable;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.filter.NewsFilter;

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

    List<NewsDto> findEntityByFilter(NewsFilter filter, int pageNumber, int pageSize);
}
