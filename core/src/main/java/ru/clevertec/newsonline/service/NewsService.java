package ru.clevertec.newsonline.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.newsonline.exception.NotFoundException;
import ru.clevertec.newsonline.serviceinteface.INewsRepository;
import ru.clevertec.newsonline.serviceinteface.INewsService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class NewsService<E> implements INewsService<E> {

    private final INewsRepository<E> newsRepository;

    public Optional<E> findById(UUID id) {
        Optional<E> news = newsRepository.findById(id);
        news.orElseThrow(() -> new NotFoundException("Новость не найдена по id"));
        return news;
    }

    public List<E> findAllNews(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return newsRepository.findAll(pageable).stream().toList();
    }

    public Optional<E> create(E e) {
        newsRepository.save(e);
        return Optional.ofNullable(e);
    }

    public E update(UUID id, E update) {
        try {
            newsRepository.findById(id).orElseThrow(() -> new NotFoundException("Обновляемая новость не найдена по id"));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        E save =(E) newsRepository.save(update);
        return save;
    }

    public void delete(UUID id) {
        Optional<E> news = newsRepository.findById(id);
        news.ifPresentOrElse(newsRepository::delete, () -> {
            throw new NotFoundException("Удаляемая новость не найдено по id");
        });
    }
}
