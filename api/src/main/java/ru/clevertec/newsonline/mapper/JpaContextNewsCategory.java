package ru.clevertec.newsonline.mapper;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;
import ru.clevertec.newsonline.entity.Category;
import ru.clevertec.newsonline.entity.News;

@RequiredArgsConstructor
@Component
public class JpaContextNewsCategory {

    private final EntityManager entityManager;
    private Category category;

    @BeforeMapping
    public void setEntity(@MappingTarget Category category) {
        this.category = category;
    }

    @AfterMapping
    private void establishRelation(@MappingTarget News news) {
        news.setCategory(category);
    }

}
