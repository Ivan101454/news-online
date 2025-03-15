package ru.clevertec.newsonline.newService.service;

import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.newsonline.newService.dto.CategoryDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.enums.Section;
import ru.clevertec.newsonline.newService.service.interfaces.CategoryPersistencePort;
import ru.clevertec.newsonline.newService.service.interfaces.CategoryServicePort;

import java.util.Optional;

@Transactional
public class CategoryService implements CategoryServicePort {

    private final CategoryPersistencePort categoryPersistencePort;

    public CategoryService(CategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }


    @Override
    public void addNews(Section section, NewsDto newsDto) {
        categoryPersistencePort.addNewsToCategory(section, newsDto);
    }
}
