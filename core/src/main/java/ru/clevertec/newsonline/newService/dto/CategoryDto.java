package ru.clevertec.newsonline.newService.dto;


import ru.clevertec.newsonline.newService.enums.Section;

import java.util.List;

public record CategoryDto(Section section, List<NewsDto> newsList) {
}
