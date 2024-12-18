package ru.clevertec.newsonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.newsonline.entity.News;

import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<News, UUID> {
}
