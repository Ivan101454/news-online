package ru.clevertec.newsonline.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.newsonline.serviceinteface.IRepository;

@Service
@Transactional
public class CommentService<E> extends CrudService<E> {

    public CommentService(IRepository<E> newsRepository) {
        super(newsRepository);
    }
}
