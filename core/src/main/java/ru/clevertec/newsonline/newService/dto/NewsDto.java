package ru.clevertec.newsonline.newService.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record NewsDto (
        @NotEmpty(message = "{catalogue.errors.news.title_header_is_invalid}")
        String headerNews,
        LocalDateTime dateOfNews,
        @Min(value = 1000000, message = "{catalogue.errors.news.number_article_less_is_required_invalid}")
        @Max(value = 9999999, message = "{catalogue.errors.news.number_article_more_is_required_invalid}")
        int articleId,
        @NotNull
        Boolean isPublished,
        @NotEmpty(message = "{catalogue.errors.news.short_description_is_invalid}")
        String shortDescription,
        List<PictureDto> pictures,
        List<CommentDto> comments) {
}
