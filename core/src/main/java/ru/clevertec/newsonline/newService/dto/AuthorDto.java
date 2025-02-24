package ru.clevertec.newsonline.newService.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AuthorDto(String nameAuthor, String lastName, LocalDateTime dateOfRegistration,
                        String phoneNumber, String email, List<NewsDto> writeNews) {
}
