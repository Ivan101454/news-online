package ru.clevertec.newsonline.serviceinteface;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRepository<E> {

    List<E> findAll();

    Optional<E> findById(UUID id);

    E save(E e);

    void delete(E e);

    List<E> findByPage(Pageable pageable);
}
