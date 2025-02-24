package ru.clevertec.newsonline.newService.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.dto.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserPersistencePort {

    List<UserDto> findAll();

    Page<UserDto> findAll(Pageable pageable);

    Optional<UserDto> findById(UUID id);

    UserDto save(UserDto userDto);

    void delete(UUID id);

    Optional<UserDto> findUserByUsername(String username);
    String getUsername(String username);
    String getPassword(String username);
    GrantedAuthority getRole(String username);
}
