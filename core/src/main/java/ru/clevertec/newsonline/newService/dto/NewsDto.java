package ru.clevertec.newsonline.newService.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record NewsDto(
        @NotEmpty(message = "{catalogue.errors.news.title_header_is_invalid}")
        String headerNews,
        @Min(value = 1000000, message = "{catalogue.errors.news.number_article_less_is_required_invalid}")
        @Max(value = 9999999, message = "{catalogue.errors.news.number_article_more_is_required_invalid}")
        int articleId,
        @NotNull(message = "{catalogue.errors.news.author_is_invalid}")
        AuthorDto author,
        boolean isPublished,
        @NotNull(message = "{catalogue.errors.news.category_is_invalid}")
        CategoryDto category,
        @NotEmpty(message = "{catalogue.errors.news.shortDescription_is_invalid}")
        String shortDescription,
        @NotEmpty(message = "{catalogue.errors.news.content_link_is_invalid}")
        String contentLink,
        List<PictureDto> pictures,
        List<CommentDto> comments) {
}
