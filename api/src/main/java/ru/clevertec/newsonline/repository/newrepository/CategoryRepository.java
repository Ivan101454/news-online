package ru.clevertec.newsonline.repository.newrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.newsonline.entity.Category;
import ru.clevertec.newsonline.newService.enums.Section;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Optional<Category> findBySection(Section section);
}
