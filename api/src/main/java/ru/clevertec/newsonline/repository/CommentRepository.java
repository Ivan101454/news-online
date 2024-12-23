package ru.clevertec.newsonline.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.newsonline.entity.Comment;
import ru.clevertec.newsonline.entity.Comment_;
import ru.clevertec.newsonline.filter.CommentFilter;
import ru.clevertec.newsonline.serviceinteface.IFilterRepository;
import ru.clevertec.newsonline.serviceinteface.IRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID>, IRepository<Comment>, IFilterEntityRepository<Comment, CommentFilter, Comment_, String> {
    @Override
    default List<Comment> findByPage(Pageable pageable) {
        return (List<Comment>) findAll(pageable);
    }

}
