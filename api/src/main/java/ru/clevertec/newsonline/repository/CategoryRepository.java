package ru.clevertec.newsonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.newsonline.entity.Category;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
