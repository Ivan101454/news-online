package ru.clevertec.newsonline.newService.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.List;

public record AuthorDto(
        @NotEmpty(message = "{catalogue.errors.author.name_is_invalid}")
        String nameAuthor,
        @NotEmpty(message = "{catalogue.errors.author.lastName_is_invalid}")
        String lastName,
        LocalDateTime dateOfRegistration,
        @Pattern(regexp = "\\+375\\d{9}", message = "{catalogue.errors.author.phone_is_invalid}")
        String phoneNumber,
        @Email(message = "{catalogue.errors.author.email_is_invalid}")
        String email,
        List<NewsDto> writeNews) {
}
