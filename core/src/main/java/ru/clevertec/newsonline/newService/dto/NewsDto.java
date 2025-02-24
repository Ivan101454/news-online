package ru.clevertec.newsonline.newService.dto;

import java.time.LocalDateTime;
import java.util.List;

public record NewsDto(String headerNews, AuthorDto author, LocalDateTime dateOfNews,
                      boolean isPublished, CategoryDto category, String shortDescription,
                      String contentLink, List<PictureDto> pictures, List<CommentDto> comments) {
}
