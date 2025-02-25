package ru.clevertec.newsonline.newService.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record NewsDto(
        @NotEmpty(message = "{catalogue.errors.news.title_header_is_invalid}")
        String headerNews,
        @NotNull(message = "{catalogue.errors.news.author_is_invalid}")
        String author,
        LocalDateTime dateOfNews,
        boolean isPublished,
        @NotNull(message = "{catalogue.errors.news.category_is_invalid}")
        String category,
        @NotEmpty(message = "{catalogue.errors.news.shortDescription_is_invalid}")
        String shortDescription,
        @NotEmpty(message = "{catalogue.errors.news.content_link_is_invalid}")
        String contentLink,
        List<PictureDto> pictures,
        List<CommentDto> comments) {
}
