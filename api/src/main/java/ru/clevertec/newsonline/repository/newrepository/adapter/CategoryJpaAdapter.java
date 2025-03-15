package ru.clevertec.newsonline.repository.newrepository.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.mapper.JpaContextAuthor;
import ru.clevertec.newsonline.mapper.JpaContextNews;
import ru.clevertec.newsonline.mapper.JpaContextNewsCategory;
import ru.clevertec.newsonline.mapper.NewsMapper;
import ru.clevertec.newsonline.newService.dto.CategoryDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.enums.Section;
import ru.clevertec.newsonline.newService.service.interfaces.CategoryPersistencePort;
import ru.clevertec.newsonline.repository.newrepository.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CategoryJpaAdapter implements CategoryPersistencePort {

    private final CategoryRepository categoryRepository;
    private final NewsMapper newsMapper;
    private final JpaContextNewsCategory ctxNC;
    private final JpaContextNews jpaCtx;
    private final JpaContextAuthor jpaCtxA;

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(newsMapper::categoryToCategoryDto).toList();
    }

    @Override
    public Optional<CategoryDto> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        categoryRepository.save(newsMapper.categoryDtoToCategory(categoryDto, ctxNC));
        return categoryDto;
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public Optional<CategoryDto> findBySection(Section section) {
        return categoryRepository.findBySection(section).map(newsMapper::categoryToCategoryDto);
    }

    @Override
    public void addNewsToCategory(Section section, NewsDto newsDto) {
        News news = newsMapper.newsDtoToNews(newsDto, jpaCtx, jpaCtxA);
        categoryRepository.findBySection(section).ifPresent(cat -> cat.addNews(news));
    }
}
