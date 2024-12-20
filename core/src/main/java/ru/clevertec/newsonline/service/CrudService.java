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
    private final IFilterRepository<E> iFilterRepositoryRepository;

    public CrudService(IRepository<E> newsRepository, IFilterRepository<E> iFilterRepositoryRepository) {
        this.iRepository = newsRepository;
        this.iFilterRepositoryRepository = iFilterRepositoryRepository;
    }

    public Optional<E> findById(UUID id) {
        Optional<E> news = iRepository.findById(id);
        news.orElseThrow(() -> new NotFoundException("Новость не найдена по id"));
        return news;
    }

    public List<E> findAll() {
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return iRepository.findAll().stream().toList();
    }

    public List<E> findByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return iRepository.findByPage(pageable).stream().toList();
    }

    public Optional<E> create(E e) {
        iRepository.save(e);
        return Optional.ofNullable(e);
    }

    public E update(UUID id, E update) {
        try {
            iRepository.findById(id).orElseThrow(() -> new NotFoundException("Обновлямое не найдена по id"));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        E save = iRepository.save(update);
        return save;
    }

    public void delete(UUID id) {
        Optional<E> news = iRepository.findById(id);
        news.ifPresentOrElse(iRepository::delete, () -> {
            throw new NotFoundException("Удаляемая новость не найдено по id");
        });
    }

    public List<E> findEntityByFilter(F f, int pageNumber, int pageSize) {

        Class<F> clazz = (Class<F>) f.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        List<Field> list = Arrays.stream(declaredFields).filter(Objects::nonNull).toList();
        HashMap<String, String> fieldObjectHashMap = new HashMap<>();
        for(Field field: list) {
            field.setAccessible(true);
            try {
                fieldObjectHashMap.put(field.getName(), String.valueOf(field.get(f)));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return iFilterRepositoryRepository.filterWord(fieldObjectHashMap, pageable);
    }
}
