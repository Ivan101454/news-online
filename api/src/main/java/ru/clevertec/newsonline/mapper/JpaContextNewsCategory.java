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
    private News news;

    @BeforeMapping
    public void setEntity(@MappingTarget News news) {
        this.news = news;
    }

    @AfterMapping
    private void establishRelation(@MappingTarget Category category) {
        category.addNews(news);
    }

}
