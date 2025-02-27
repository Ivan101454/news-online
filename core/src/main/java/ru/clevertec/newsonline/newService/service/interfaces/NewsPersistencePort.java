package ru.clevertec.newsonline.newService.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.filter.NewsFilter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NewsPersistencePort{

    Optional<NewsDto> findByArticleId(int id);

    List<NewsDto> findAll();

    Page<NewsDto> findAll(Pageable pageable);

    Optional<NewsDto> findById(UUID id);

    NewsDto save(NewsDto newsDto);

    void deleteByArticleId(int id);

    List<NewsDto> filterWord(NewsFilter newsFilter, Pageable pageable);

}
