package ru.clevertec.newsonline.serviceinteface;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICrudService<E, F> {

    Optional<E> findById(UUID id);

    List<E> findAll();

    List<E> findByPage(int pageNumber, int pageSize);

    Optional<E> create(E e);

    E update(UUID id, E e);

    void delete(UUID id);

    List<E> findEntityByFilter(F f, Class<E> entityClazz, int pageNumber, int pageSize);
}
