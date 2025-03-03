package ru.clevertec.newsonline.newService.dto;


import jakarta.validation.constraints.NotNull;
import ru.clevertec.newsonline.newService.enums.Section;

import java.util.List;
import java.util.UUID;

public record CategoryDto(
        @NotNull(message = "{catalogue.errors.category.section_is_invalid}")
        Section section,
        List<NewsDto> newsList) {
}
