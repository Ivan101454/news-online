package ru.clevertec.newsonline.newService.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.clevertec.newsonline.newService.dto.CategoryDto;
import ru.clevertec.newsonline.newService.dto.CommentDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.dto.PictureDto;
import ru.clevertec.newsonline.newService.filter.NewsFilter;
import ru.clevertec.newsonline.newService.service.interfaces.NewsPersistencePort;
import ru.clevertec.newsonline.newService.service.interfaces.NewsServicePort;
import ru.clevertec.newsonline.util.SaveImage;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Transactional
public class NewsService implements NewsServicePort {

    private final NewsPersistencePort newsPersistencePort;
    @Value("${app.upload.path}")
    String uploadDir;

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

    public void update(int id, NewsDto update, CategoryDto categoryDto) {
        newsPersistencePort.findByArticleId(id).orElseThrow(() -> new NoSuchElementException("Сущность не найдена по id"));
        newsPersistencePort.update(id, update, categoryDto);
    }

    public void delete(int articleId) {
        Optional<NewsDto> entity = newsPersistencePort.findByArticleId(articleId);
        entity.ifPresentOrElse(x -> newsPersistencePort.deleteByArticleId(articleId), () -> {
            throw new NoSuchElementException("Удаляемая сушность не найдено по id");
        });
    }

    public List<NewsDto> findEntityByFilter(NewsFilter filter, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return newsPersistencePort.filterWord(filter, pageable);
    }

    public void addComment(int articleId, CommentDto commentDto) {
        newsPersistencePort.addCommentToNewsList(articleId, commentDto);
    }

    public void addPicture(int articleId, MultipartFile image) {
        String path = saveImage(image);
        PictureDto pictureDto = new PictureDto(null, image.getOriginalFilename(), path);
        newsPersistencePort.addPictureToNewsList(articleId, pictureDto);
    }

    public String saveImage(MultipartFile image) {
        return SaveImage.persist(image, uploadDir);
    }

}
