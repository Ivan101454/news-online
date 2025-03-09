package ru.clevertec.newsonline.client;

import ru.clevertec.newsonline.newService.dto.CommentDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;

import java.util.List;
import java.util.Optional;

public interface NewsRestClient {
    List<NewsDto> findAllNews();
    List<NewsDto> findNewsWithPagination(int pageNumber, int pageSize);
    List<NewsDto> findNewsByFilter(String headerNews, String shortDescription,
                                       int pageNumber, int pageSize);
    NewsDto createNews(NewsDto newsDto);
    Optional<NewsDto> findNews(int articleId);
    void updateNews(NewsDto updateNewsDto);
    void deleteNews(int articleId);
    List<CommentDto> getComments(int articleId);
    void addComment(int articleId, CommentDto commentDto);
}
