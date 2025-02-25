package ru.clevertec.newsonline.newService.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CommentDto(
        LocalDateTime dateOfComment,
        @NotEmpty(message = "{catalogue.errors.comment.text_is_invalid}")
        String textComment,
        @NotNull(message = "{catalogue.errors.comment.text_is_invalid}")
        UserDto authorComment,
        @NotNull(message = "{}")
        NewsDto news) {
}
