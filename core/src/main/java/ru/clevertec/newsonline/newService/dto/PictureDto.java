package ru.clevertec.newsonline.newService.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record PictureDto(
        UUID pictureId,
        @NotEmpty(message = "{catalogue.errors.picture.name_is_invalid}")
        String nameOfPicture,
        @NotEmpty(message = "{catalogue.errors.picture.content_link_is_invalid}")
        String linkOnPicture) {
}
