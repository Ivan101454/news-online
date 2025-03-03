package ru.clevertec.newsonline.newService.service.interfaces;

import org.springframework.data.domain.Pageable;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.dto.UserDto;
import ru.clevertec.newsonline.newService.filter.UserFilter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserServicePort {

    Optional<UserDto> findById(UUID id);

    List<UserDto> findAll();

    List<UserDto> findByPage(int pageNumber, int pageSize);

    Optional<UserDto> create(UserDto userDto);

    void update(String username, UserDto userDto);

    void delete(String username);

    Optional<UserDto> findUserByUsername(String username);

    List<UserDto> findEntityByFilter(UserFilter filer, Pageable pageable);
}
