package ru.clevertec.newsonline.repository.newrepository.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.clevertec.newsonline.mapper.NewsMapper;
import ru.clevertec.newsonline.newService.dto.CommentDto;
import ru.clevertec.newsonline.newService.service.interfaces.CommentPersistencePort;
import ru.clevertec.newsonline.repository.newrepository.CommentRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CommentJpaAdapter implements CommentPersistencePort {

    private final CommentRepository commentRepository;
    private final NewsMapper newsMapper;

    @Override
    public List<CommentDto> findAll() {
        return commentRepository.findAll().stream().map(newsMapper::commentToCommentDto).toList();
    }

    @Override
    public Page<CommentDto> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable).map(newsMapper::commentToCommentDto);
    }

    @Override
    public Optional<CommentDto> findById(UUID id) {
        return commentRepository.findById(id).map(newsMapper::commentToCommentDto);
    }

    @Override
    public CommentDto save(CommentDto commentDto) {
        commentRepository.save(newsMapper.commentDtoToComment(commentDto));
        return commentDto;
    }

    @Override
    public void delete(UUID id) {
        commentRepository.findById(id)
                .ifPresentOrElse(commentRepository::delete, () -> {throw new NoSuchElementException("Нет такого комментария");});
    }
}
