package ru.clevertec.newsonline.newService.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentDto(
        UUID commentId,
        LocalDateTime dateOfComment,
        @NotEmpty(message = "{catalogue.errors.comment.text_is_invalid}")
        String textComment,
        @NotNull(message = "{catalogue.errors.comment.text_is_invalid}")
        UserDto authorComment) {
}
