package ru.clevertec.newsonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import ru.clevertec.newsonline.entity.User;
import ru.clevertec.newsonline.filter.UserFilter;
import ru.clevertec.newsonline.serviceinteface.IFilterRepository;
import ru.clevertec.newsonline.serviceinteface.IRepository;
import ru.clevertec.newsonline.serviceinteface.IUserRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, IRepository<User>, IUserRepository<User>, IFilterRepository<User, UserFilter> {

    @Override
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
