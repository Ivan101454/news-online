package ru.clevertec.newsonline.newService.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.newsonline.newService.dto.NewsDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NewsPersistencePort {

    List<NewsDto> findAll();

    Page<NewsDto> findAll(Pageable pageable);

    Optional<NewsDto> findById(UUID id);

    NewsDto save(NewsDto newsDto);

    void delete(UUID id);

}
