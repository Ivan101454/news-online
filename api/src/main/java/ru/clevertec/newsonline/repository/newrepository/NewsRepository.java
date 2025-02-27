package ru.clevertec.newsonline.repository.newrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.newsonline.entity.News;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<News, UUID> {

    Optional<News> findByArticleId(int id);
    void deleteByArticleId(int id);
}
