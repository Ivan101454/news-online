package ru.clevertec.newsonline.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.newsonline.serviceinteface.IRepository;

@Transactional
public class NewsService<E> extends CrudService<E>{

    private final IRepository<E> newsRepository;

    public NewsService(IRepository<E> newsRepository) {
        super(newsRepository);
        this.newsRepository = newsRepository;
    }
}
