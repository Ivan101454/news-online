package ru.clevertec.newsonline.client;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;
import ru.clevertec.newsonline.newService.dto.CategoryDto;
import ru.clevertec.newsonline.newService.dto.CommentDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class RestClientNewsRestClient implements NewsRestClient {

    private final static ParameterizedTypeReference<List<NewsDto>> NEWS_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {
            };

    private final RestClient restClient;

    @Override
    public List<NewsDto> findAllNews() {
        return restClient
                .get()
                .uri("/catalogue-api/news/list")
                .retrieve()
                .body(NEWS_TYPE_REFERENCE);
    }

    @Override
    public List<NewsDto> findNewsWithPagination(int pageNumber, int pageSize) {
        return restClient
                .get()
                .uri("/catalogue-api/news/list-pagination?pageNumber={pageNumber}&pageSize={pageSize}", pageNumber, pageSize)
                .retrieve()
                .body(NEWS_TYPE_REFERENCE);
    }

    @Override
    public List<NewsDto> findNewsByFilter(String headerNews, String shortDescription, int pageNumber, int pageSize) {
        return restClient
                .get()
                .uri("/catalogue-api/news/list-by-filter?headerNews={headerNews}&shortDescription={shortDescription}&pageNumber={pageNumber}&pageSize={pageSize}", headerNews, shortDescription, pageNumber, pageSize)
                .retrieve()
                .body(NEWS_TYPE_REFERENCE);
    }

    @Override
    public NewsDto createNews(NewsDto newsDto, CategoryDto categoryDto, MultipartFile image) {
        try {
            MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
            parts.add("newsDto", newsDto);
            parts.add("categoryDto", categoryDto);

            if (image != null && !image.isEmpty()) {
                HttpHeaders fileHeaders = new HttpHeaders();
                fileHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
                parts.add("image", new HttpEntity<>(image.getResource(), fileHeaders));
            }

            return restClient
                    .post()
                    .uri("/catalogue-api/news")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(parts)
                    .retrieve()
                    .body(NewsDto.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<NewsDto> findNews(int articleId) {
        try {
            return Optional.of(restClient.get()
                    .uri("/catalogue-api/news/{newsArticle}", articleId)
                    .retrieve()
                    .body(NewsDto.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateNews(NewsDto updateNewsDto, CategoryDto categoryDto, MultipartFile image) {
        try {
            MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
            parts.add("newsDto", updateNewsDto);
            parts.add("categoryDto", categoryDto);

            if (image != null && !image.isEmpty()) {
                HttpHeaders fileHeaders = new HttpHeaders();
                fileHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
                parts.add("image", new HttpEntity<>(image.getResource(), fileHeaders));
            }

            restClient
                    .patch()
                    .uri("/catalogue-api/news/{articleId}", updateNewsDto.articleId())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(parts)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteNews(int articleId) {
        try {
            Optional.of(restClient.delete()
                    .uri("/catalogue-api/news/{newsArticle}", articleId)
                    .retrieve()
                    .toBodilessEntity());
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public List<CommentDto> getComments(int articleId) {
        return restClient
                .get()
                .uri("/catalogue-api/news/{newsArticle}", articleId)
                .retrieve()
                .body(NewsDto.class).comments();
    }

    @Override
    public void addComment(int articleId, CommentDto commentDto) {
        try {
            restClient
                    .patch()
                    .uri("/catalogue-api/news/{articleId}/add-comment", articleId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(commentDto)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }
}
