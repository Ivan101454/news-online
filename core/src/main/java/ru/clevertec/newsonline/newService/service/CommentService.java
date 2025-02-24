package ru.clevertec.newsonline.newService.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.newsonline.exception.NotFoundException;
import ru.clevertec.newsonline.newService.dto.CommentDto;
import ru.clevertec.newsonline.newService.service.interfaces.CommentPersistencePort;
import ru.clevertec.newsonline.newService.service.interfaces.CommentServicePort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
public class CommentService implements CommentServicePort {

    private final CommentPersistencePort commentPersistencePort;

    public CommentService(CommentPersistencePort commentPersistencePort) {
        this.commentPersistencePort = commentPersistencePort;
    }
    @Cacheable(value = "byIdCache", key = "#p0")
    public Optional<CommentDto> findById(UUID id) {
        Optional<CommentDto> entity = commentPersistencePort.findById(id);
        entity.orElseThrow(() -> new NotFoundException("Сущность не найдена по id"));
        return entity;
    }

    public List<CommentDto> findAll() {
        return commentPersistencePort.findAll();
    }

    public List<CommentDto> findByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return commentPersistencePort.findAll(pageable).getContent();
    }

    public Optional<CommentDto> create(CommentDto commentDto) {
        commentPersistencePort.save(commentDto);
        return Optional.ofNullable(commentDto);
    }

    public void update(UUID id, CommentDto update) {
        try {
            commentPersistencePort.findById(id).orElseThrow(() -> new NotFoundException("Сущность не найдена по id"));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        commentPersistencePort.save(update);
    }

    public void delete(UUID id) {
        Optional<CommentDto> entity = commentPersistencePort.findById(id);
        entity.ifPresentOrElse(x -> commentPersistencePort.delete(id), () -> {
            throw new NotFoundException("Удаляемая сушность не найдено по id");
        });
    }

}
