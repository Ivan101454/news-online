package ru.clevertec.newsonline.newService.service.interfaces;

import org.springframework.web.multipart.MultipartFile;
import ru.clevertec.newsonline.newService.dto.CategoryDto;
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

    void update(int articleId, NewsDto newsDto, CategoryDto categoryDto);

    void delete(int articleId);

    List<NewsDto> findEntityByFilter(NewsFilter filter, int pageNumber, int pageSize);

    void addComment(int articleId, CommentDto commentDto);

    void addPicture(int articleId, MultipartFile image);

    String saveImage(MultipartFile image);
}
