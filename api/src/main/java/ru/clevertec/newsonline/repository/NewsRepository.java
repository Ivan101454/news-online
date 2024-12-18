package ru.clevertec.newsonline.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.serviceinteface.IRepository;

import java.util.UUID;

@Repository
@Primary
public interface NewsRepository extends JpaRepository<News, UUID>, IRepository<News> {

}
