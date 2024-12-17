package ru.clevertec.newsonline.dto;

import java.util.List;

public record PictureDto(String nameOfPicture, String linkOnPicture,
                         List<NewsDto> news) {
}
