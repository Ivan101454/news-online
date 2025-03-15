package ru.clevertec.newsonline.newService.service.interfaces;

import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.enums.Section;

public interface CategoryServicePort {

    void addNews(Section section, NewsDto newsDto);
}
