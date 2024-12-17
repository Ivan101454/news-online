package ru.clevertec.newsonline.dto;

import ru.clevertec.newsonline.enums.Section;

import java.util.List;

public record CategoryDto(Section section, List<NewsDto> newsList) {
}
