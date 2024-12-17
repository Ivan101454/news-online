package ru.clevertec.newsonline.dto;

import java.time.LocalDateTime;
import java.util.List;

public record NewsDto(String headerNews, AuthorDto authorNews, LocalDateTime dateOfNews,
                      boolean isPublished, CategoryDto category, String shortDescription,
                      String bodyNews, List<PictureDto> pictures, List<CommentDto> comments) {
}
