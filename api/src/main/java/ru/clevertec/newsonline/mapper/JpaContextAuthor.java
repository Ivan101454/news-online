package ru.clevertec.newsonline.mapper;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;
import ru.clevertec.newsonline.entity.Author;
import ru.clevertec.newsonline.entity.News;

@RequiredArgsConstructor
@Component
public class JpaContextAuthor {

    private final EntityManager entityManager;
    private Author author;

    @BeforeMapping
    public void setEntity(@MappingTarget Author author) {
        this.author = author;
    }

    @AfterMapping
    private void establishRelation(@MappingTarget News news) {
        news.setAuthor(author);
    }
}
