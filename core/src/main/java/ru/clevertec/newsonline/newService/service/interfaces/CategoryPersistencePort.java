package ru.clevertec.newsonline.newService.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.newsonline.newService.dto.CategoryDto;
import ru.clevertec.newsonline.newService.dto.CommentDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.enums.Section;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryPersistencePort {

    List<CategoryDto> findAll();

    Optional<CategoryDto> findById(UUID id);

    CategoryDto save(CategoryDto categoryDto);

    void delete(UUID id);

    Optional<CategoryDto> findBySection(Section section);

    void addNewsToCategory(Section section, NewsDto newsDto);

}
