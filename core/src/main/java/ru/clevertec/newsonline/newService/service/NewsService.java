package ru.clevertec.newsonline.newService.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.newsonline.exception.NotFoundException;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.service.interfaces.NewsPersistencePort;
import ru.clevertec.newsonline.newService.service.interfaces.NewsServicePort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
public class NewsService implements NewsServicePort {

    private final NewsPersistencePort newsPersistencePort;

    public NewsService(NewsPersistencePort newsPersistencePort) {
        this.newsPersistencePort = newsPersistencePort;
    }
    @Cacheable(value = "byIdCache", key = "#p0")
    public Optional<NewsDto> findById(UUID id) {
        Optional<NewsDto> entity = newsPersistencePort.findById(id);
        entity.orElseThrow(() -> new NotFoundException("Сущность не найдена по id"));
        return entity;
    }

    public List<NewsDto> findAll() {
        return newsPersistencePort.findAll();
    }

    public List<NewsDto> findByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return newsPersistencePort.findAll(pageable).getContent();
    }

    public Optional<NewsDto> create(NewsDto newsDto) {
        newsPersistencePort.save(newsDto);
        return Optional.ofNullable(newsDto);
    }

    public void update(UUID id, NewsDto update) {
        try {
            newsPersistencePort.findById(id).orElseThrow(() -> new NotFoundException("Сущность не найдена по id"));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        newsPersistencePort.save(update);
    }

    public void delete(UUID id) {
        Optional<NewsDto> entity = newsPersistencePort.findById(id);
        entity.ifPresentOrElse(x -> newsPersistencePort.delete(id), () -> {
            throw new NotFoundException("Удаляемая сушность не найдено по id");
        });
    }

}
