package ru.clevertec.newsonline.serviceinteface;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@NoRepositoryBean
public interface INewsRepository<E> extends Repository<E, UUID> {

    List<E> findAll(Pageable pageable);

    Optional<E> findById(UUID id);

    E save(E e);

    void delete(E e);
}
