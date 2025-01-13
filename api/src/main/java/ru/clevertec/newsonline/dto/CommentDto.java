package ru.clevertec.newsonline.dto;

import java.time.LocalDateTime;

public record CommentDto(LocalDateTime dateOfComment, String textComment,
                         UserDto authorComment, NewsDto news) {
}
