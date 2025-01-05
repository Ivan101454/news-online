package ru.clevertec.newsonline.serviceinteface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRepository<E> {

    List<E> findAll();

    Page<E> findAll(Pageable pageable);

    Optional<E> findById(UUID id);

    E save(E e);

    void delete(E e);

}
