package ru.clevertec.newsonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.newsonline.entity.Comment;
import ru.clevertec.newsonline.filter.CommentFilter;
import ru.clevertec.newsonline.serviceinteface.IRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID>, IRepository<Comment>, IFilterEntityRepository<Comment, CommentFilter> {

}
