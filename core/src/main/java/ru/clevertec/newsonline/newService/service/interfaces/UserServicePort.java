package ru.clevertec.newsonline.newService.service.interfaces;

import org.springframework.data.domain.Pageable;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.dto.UserDto;
import ru.clevertec.newsonline.newService.filter.UserFilter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserServicePort {

    List<UserDto> findAll();

    List<UserDto> findByPage(int pageNumber, int pageSize);

    Optional<UserDto> create(UserDto userDto);

    void update(UUID id, UserDto userDto);

    void delete(UUID id);

    List<UserDto> findEntityByFilter(UserFilter filer, Pageable pageable);
}
