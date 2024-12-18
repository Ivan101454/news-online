package ru.clevertec.newsonline.serviceinteface;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICrudService<E> {

    Optional<E> findById(UUID id);

    List<E> findAll();

    Optional<E> create(E e);

    E update(UUID id, E e);

    void delete(UUID id);
}
