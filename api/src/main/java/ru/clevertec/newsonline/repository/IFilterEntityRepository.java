package ru.clevertec.newsonline.repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFilterEntityRepository<E, F> {
    List<E> filterWord(F filter, Class<E> entityClazz, Pageable pageable);
}
