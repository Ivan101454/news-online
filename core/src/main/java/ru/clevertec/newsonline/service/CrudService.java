package ru.clevertec.newsonline.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.newsonline.exception.NotFoundException;
import ru.clevertec.newsonline.serviceinteface.IRepository;
import ru.clevertec.newsonline.serviceinteface.ICrudService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
public abstract class CrudService<E> implements ICrudService<E> {

    private final IRepository<E> newsRepository;

    public CrudService(IRepository<E> newsRepository) {
        this.newsRepository = newsRepository;
    }

    public Optional<E> findById(UUID id) {
        Optional<E> news = newsRepository.findById(id);
        news.orElseThrow(() -> new NotFoundException("Новость не найдена по id"));
        return news;
    }

    public List<E> findAll() {
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return newsRepository.findAll().stream().toList();
    }

    public Optional<E> create(E e) {
        newsRepository.save(e);
        return Optional.ofNullable(e);
    }

    public E update(UUID id, E update) {
        try {
            newsRepository.findById(id).orElseThrow(() -> new NotFoundException("Обновлямое не найдена по id"));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        E save = newsRepository.save(update);
        return save;
    }

    public void delete(UUID id) {
        Optional<E> news = newsRepository.findById(id);
        news.ifPresentOrElse(newsRepository::delete, () -> {
            throw new NotFoundException("Удаляемая новость не найдено по id");
        });
    }
}
