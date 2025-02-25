package ru.clevertec.newsonline.newService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PictureDto(
        @NotEmpty(message = "{catalogue.errors.picture.name_is_invalid}")
        String nameOfPicture,
        @NotEmpty(message = "{catalogue.errors.picture.content_link_is_invalid}")
        String linkOnPicture,
        List<NewsDto> news) {
}
