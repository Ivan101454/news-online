package ru.clevertec.newsonline.newService.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record NewsDto(
        UUID newsId,
        @NotEmpty(message = "{catalogue.errors.news.title_header_is_invalid}")
        String headerNews,
        LocalDateTime dateOfNews,
        @Min(value = 1000000, message = "{catalogue.errors.news.number_article_less_is_required_invalid}")
        @Max(value = 9999999, message = "{catalogue.errors.news.number_article_more_is_required_invalid}")
        int articleId,
        boolean isPublished,
        @NotEmpty(message = "{catalogue.errors.news.shortDescription_is_invalid}")
        String shortDescription,
        @NotEmpty(message = "{catalogue.errors.news.content_link_is_invalid}")
        String contentLink,
        List<PictureDto> pictures,
        List<CommentDto> comments) {
}
