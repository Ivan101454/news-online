package ru.clevertec.newsonline.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.newsonline.serviceinteface.IRepository;

@Service
@Transactional
public class NewsService<E> extends CrudService<E>{

    public NewsService(IRepository<E> iRepository) {
        super(iRepository);
    }

}
