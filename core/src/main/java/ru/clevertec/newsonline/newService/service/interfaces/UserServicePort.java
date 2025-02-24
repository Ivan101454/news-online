package ru.clevertec.newsonline.newService.service.interfaces;

import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.dto.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserServicePort {

    Optional<UserDto> findById(UUID id);

    List<UserDto> findAll();

    List<UserDto> findByPage(int pageNumber, int pageSize);

    Optional<UserDto> create(UserDto userDto);

    void update(UUID id, UserDto userDto);

    void delete(UUID id);
}
