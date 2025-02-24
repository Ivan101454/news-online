package ru.clevertec.newsonline.repository.newrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.newsonline.entity.Comment;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID>{

}
