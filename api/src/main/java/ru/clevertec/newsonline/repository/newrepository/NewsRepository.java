package ru.clevertec.newsonline.repository.newrepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.newsonline.entity.Category;
import ru.clevertec.newsonline.entity.News;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<News, UUID> {

    Optional<News> findByArticleId(int id);
    List<News> findByCategory(Category category, Pageable pageable);

}
