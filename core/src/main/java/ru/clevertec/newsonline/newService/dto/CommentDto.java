package ru.clevertec.newsonline.newService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentDto(
        LocalDateTime dateOfComment,
        @NotBlank(message = "{catalogue.errors.comment.text_is_invalid}")
        String textComment) {
}
