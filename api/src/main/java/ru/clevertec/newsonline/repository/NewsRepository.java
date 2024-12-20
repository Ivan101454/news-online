package ru.clevertec.newsonline.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.serviceinteface.IRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<News, UUID>, IRepository<News>, IFilterEntityRepository<News> {
    @Override
    default List<News> findByPage(Pageable pageable) {
        return (List<News>) findAll(pageable);
    }
}
