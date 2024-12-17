package ru.clevertec.newsonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.newsonline.entity.Picture;

import java.util.UUID;

public interface PictureRepository extends JpaRepository<Picture, UUID> {
}
