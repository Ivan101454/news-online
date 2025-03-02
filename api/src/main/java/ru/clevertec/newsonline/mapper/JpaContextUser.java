package ru.clevertec.newsonline.mapper;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;
import ru.clevertec.newsonline.entity.Comment;
import ru.clevertec.newsonline.entity.User;

@RequiredArgsConstructor
@Component
public class JpaContextUser {

    private final EntityManager entityManager;
    private User user;

    @BeforeMapping
    public void setEntity(@MappingTarget User user) {
        this.user = user;
    }

    @AfterMapping
    private void establishRelation(@MappingTarget Comment comment) {
        comment.setAuthorComment(user);
    }
}
