package ru.clevertec.newsonline.service;

import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.newsonline.serviceinteface.IRepository;

@Transactional
public class CommentService<E> extends CrudService<E> {

    private final IRepository<E> newsRepository;

    public CommentService(IRepository<E> newsRepository) {
        super(newsRepository);
        this.newsRepository = newsRepository;
    }
}
