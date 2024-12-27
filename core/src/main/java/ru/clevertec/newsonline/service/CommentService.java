package ru.clevertec.newsonline.service;

import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.newsonline.serviceinteface.IFilterRepository;
import ru.clevertec.newsonline.serviceinteface.IRepository;


@Transactional
public class CommentService<E, F> extends CrudService<E, F> {

    private final IRepository<E> iRepository;
    private final IFilterRepository<E, F> iFilterRepository;

    public CommentService(IRepository<E> iRepository, IFilterRepository<E, F> iFilterRepository) {
        super(iRepository, iFilterRepository);
        this.iRepository = iRepository;
        this.iFilterRepository = iFilterRepository;
    }



}
