package ru.clevertec.newsonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.newsonline.entity.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
