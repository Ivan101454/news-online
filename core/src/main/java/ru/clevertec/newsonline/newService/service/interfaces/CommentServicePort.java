package ru.clevertec.newsonline.newService.service.interfaces;

import org.springframework.data.domain.Pageable;
import ru.clevertec.newsonline.newService.dto.CommentDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.dto.UserDto;
import ru.clevertec.newsonline.newService.filter.CommentFilter;
import ru.clevertec.newsonline.newService.filter.NewsFilter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentServicePort {

    List<CommentDto> findAll();

    List<CommentDto> findByPage(int pageNumber, int pageSize);

    Optional<CommentDto> create(CommentDto commentDto);

    void update(UUID id, CommentDto commentDto);

    void delete(UUID id);

    List<CommentDto> findEntityByFilter(CommentFilter filter, Pageable pageable);
}
