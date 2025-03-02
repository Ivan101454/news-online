package ru.clevertec.newsonline.repository.newrepository.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.clevertec.newsonline.mapper.JpaContextAuthor;
import ru.clevertec.newsonline.mapper.NewsMapper;
import ru.clevertec.newsonline.newService.dto.AuthorDto;
import ru.clevertec.newsonline.newService.service.interfaces.AuthorPersistencePort;
import ru.clevertec.newsonline.repository.newrepository.AuthorRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AuthorJpaAdapter implements AuthorPersistencePort {

    private final AuthorRepository authorRepository;
    private final NewsMapper newsMapper;
    private final JpaContextAuthor ctxA;

    @Override
    public Optional<AuthorDto> findByNameAndLastName(String firstname, String lastname) {
        return authorRepository.findByNameAuthorIgnoreCaseAndLastNameIgnoreCase(firstname, lastname)
                .map(newsMapper::authorToAuthorDto);
    }

    @Override
    public List<AuthorDto> findAll() {
        return authorRepository.findAll().stream().map(newsMapper::authorToAuthorDto).toList();
    }

    @Override
    public List<AuthorDto> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable).stream().map(newsMapper::authorToAuthorDto).toList();
    }

    @Override
    public AuthorDto save(AuthorDto authorDto) {
        authorRepository.save(newsMapper.authorDtoToAuthor(authorDto, ctxA));
        return authorDto;
    }

    @Override
    public void delete(UUID id) {
        authorRepository.findById(id)
                .ifPresentOrElse(authorRepository::delete, () -> {throw new NoSuchElementException("Нет такого автора");});
    }
}
