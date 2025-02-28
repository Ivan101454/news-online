package ru.clevertec.newsonline.mapper;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;
import ru.clevertec.newsonline.entity.Comment;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.newService.dto.CommentDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;

@RequiredArgsConstructor
@Component
public class JpaContext {

    private final EntityManager entityManager;
    private News news;

    @BeforeMapping
    public void setEntity(@MappingTarget News news) {
        this.news = news;
    }

    @AfterMapping
    private void establishRelation(@MappingTarget Comment comment) {
        comment.setNews(news);
    }

}
