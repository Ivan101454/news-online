package ru.clevertec.newsonline.newService.service.interfaces;

import ru.clevertec.newsonline.newService.dto.CommentDto;
import ru.clevertec.newsonline.newService.dto.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentServicePort {


    Optional<CommentDto> findById(UUID id);

    List<CommentDto> findAll();

    List<CommentDto> findByPage(int pageNumber, int pageSize);

    Optional<CommentDto> create(CommentDto commentDto);

    void update(UUID id, CommentDto commentDto);

    void delete(UUID id);
}
