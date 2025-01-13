package ru.clevertec.newsonline.service;

import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.newsonline.serviceinteface.IFilterRepository;
import ru.clevertec.newsonline.serviceinteface.IRepository;

@Transactional
public class NewsService<E, F> extends CrudService<E, F>{

    private final IRepository<E> newsRepository;
    private final IFilterRepository<E, F> iFilterRepository;

    public NewsService(IRepository<E> newsRepository, IFilterRepository<E, F> iFilterRepository) {
        super(newsRepository, iFilterRepository);
        this.newsRepository = newsRepository;
        this.iFilterRepository = iFilterRepository;
    }

}
