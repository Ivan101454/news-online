package ru.clevertec.newsonline.newService.service.interfaces;


import org.springframework.data.domain.Pageable;
import ru.clevertec.newsonline.newService.dto.AuthorDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorPersistencePort {

    Optional<AuthorDto> findByNameAndLastName(String firstname, String lastname);

    List<AuthorDto> findAll();

    List<AuthorDto> findAll(Pageable pageable);

    AuthorDto save(AuthorDto authorDto);

    void delete(UUID id);
}
