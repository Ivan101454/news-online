package ru.clevertec.newsonline.newService.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.newsonline.newService.dto.CommentDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.dto.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentPersistencePort {
    List<CommentDto> findAll();

    Page<CommentDto> findAll(Pageable pageable);

    Optional<CommentDto> findById(UUID id);

    CommentDto save(CommentDto commentDto);

    void delete(UUID id);
}
