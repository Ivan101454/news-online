package ru.clevertec.newsonline.dto;

import java.time.LocalDate;
import java.util.List;

public record AuthorDto(String nameAuthor, String lastName, LocalDate dateOfRegistration,
                        String phoneNumber, String email, List<NewsDto> writeNews) {
}
