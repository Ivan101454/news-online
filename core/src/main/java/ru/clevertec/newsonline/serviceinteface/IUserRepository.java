package ru.clevertec.newsonline.serviceinteface;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface IUserRepository<E> {

    Optional<E> findUserByUsername(String username);
    String getUsername(String username);
    String getPassword(String username);
    GrantedAuthority getRole(String username);

}
