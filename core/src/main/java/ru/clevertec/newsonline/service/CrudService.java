package ru.clevertec.newsonline.service;

import ru.clevertec.newsonline.exception.NotFoundException;
import ru.clevertec.newsonline.serviceinteface.IFilterRepository;
import ru.clevertec.newsonline.serviceinteface.IRepository;
import ru.clevertec.newsonline.serviceinteface.ICrudService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public abstract class CrudService<E, F> implements ICrudService<E, F> {

    private final IRepository<E> iRepository;
    private final IFilterRepository<E, F> iFilterRepositoryRepository;

    public CrudService(IRepository<E> newsRepository, IFilterRepository<E, F> iFilterRepositoryRepository) {
        this.iRepository = newsRepository;
        this.iFilterRepositoryRepository = iFilterRepositoryRepository;
    }

    public Optional<E> findById(UUID id) {
        Optional<E> entity = iRepository.findById(id);
        entity.orElseThrow(() -> new NotFoundException("Сущность не найдена по id"));
        return entity;
    }

    public List<E> findAll() {
        return iRepository.findAll();
    }

    public List<E> findByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return iRepository.findAll(pageable).getContent();
    }

    public Optional<E> create(E e) {
        iRepository.save(e);
        return Optional.ofNullable(e);
    }

    public E update(UUID id, E update) {
        try {
            iRepository.findById(id).orElseThrow(() -> new NotFoundException("Сущность не найдена по id"));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return iRepository.save(update);
    }

    public void delete(UUID id) {
        Optional<E> entity = iRepository.findById(id);
        entity.ifPresentOrElse(iRepository::delete, () -> {
            throw new NotFoundException("Удаляемая сушность не найдено по id");
        });
    }

    public List<E> findEntityByFilter(F f, Class<E> entityClazz, int pageNumber, int pageSize) {
        return iFilterRepositoryRepository.filterWord(f, entityClazz, pageNumber, pageSize);
    }
}
