package ru.clevertec.newsonline.newService.service;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.newsonline.exception.NotFoundException;
import ru.clevertec.newsonline.newService.dto.CommentDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.dto.PictureDto;
import ru.clevertec.newsonline.newService.filter.NewsFilter;
import ru.clevertec.newsonline.newService.service.interfaces.AuthorPersistencePort;
import ru.clevertec.newsonline.newService.service.interfaces.NewsPersistencePort;
import ru.clevertec.newsonline.newService.service.interfaces.NewsServicePort;

import java.util.List;
import java.util.Optional;

@Transactional
public class NewsService implements NewsServicePort {

    private final NewsPersistencePort newsPersistencePort;

    public NewsService(NewsPersistencePort newsPersistencePort) {
        this.newsPersistencePort = newsPersistencePort;
    }

    public Optional<NewsDto> findByArticleId(int articleId) {
        return newsPersistencePort.findByArticleId(articleId);
    }

    public List<NewsDto> findAll() {
        return newsPersistencePort.findAll();
    }

    public List<NewsDto> findByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return newsPersistencePort.findAll(pageable).getContent();
    }

    public Optional<NewsDto> create(NewsDto newsDto) {
        NewsDto saveNews = newsPersistencePort.save(newsDto);
        return Optional.of(newsDto);
    }

    public void update(int id, NewsDto update) {
        try {
            newsPersistencePort.findByArticleId(id).orElseThrow(() -> new NotFoundException("Сущность не найдена по id"));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        newsPersistencePort.update(id, update);
    }

    public void delete(int articleId) {
        Optional<NewsDto> entity = newsPersistencePort.findByArticleId(articleId);
        entity.ifPresentOrElse(x -> newsPersistencePort.deleteByArticleId(articleId), () -> {
            throw new NotFoundException("Удаляемая сушность не найдено по id");
        });
    }

    public List<NewsDto> findEntityByFilter(NewsFilter filter, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return newsPersistencePort.filterWord(filter, pageable);
    }

    public void addComment(int articleId, CommentDto commentDto) {
        newsPersistencePort.addCommentToNewsList(articleId, commentDto);
    }

    public void addPicture(int articleId, PictureDto pictureDto) {
        newsPersistencePort.addPictureToNewsList(articleId, pictureDto);
    }

}
