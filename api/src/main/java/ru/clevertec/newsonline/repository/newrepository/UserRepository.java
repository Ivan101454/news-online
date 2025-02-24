package ru.clevertec.newsonline.repository.newrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import ru.clevertec.newsonline.entity.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByUsername(String username);

    default String getUsername(String username) {
        return findUserByUsername(username).orElseThrow().getUsername();
    }
    default String getPassword(String username) {
        return findUserByUsername(username).orElseThrow().getPassword();
    }
    default GrantedAuthority getRole(String username) {
        return findUserByUsername(username).orElseThrow().getRole();
    }

}
