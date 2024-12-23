package ru.clevertec.newsonline.serviceinteface;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IFilterRepository<E, F> {

    List<E> filterWord(F f, int pageNumber, int pageSize);
}
