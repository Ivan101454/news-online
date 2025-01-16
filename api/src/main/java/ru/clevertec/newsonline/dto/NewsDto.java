package ru.clevertec.newsonline.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record NewsDto(String headerNews, AuthorDto author, LocalDateTime dateOfNews,
                      boolean isPublished, CategoryDto category, String shortDescription,
                      String contentLink, List<PictureDto> pictures, List<CommentDto> comments) {
}
