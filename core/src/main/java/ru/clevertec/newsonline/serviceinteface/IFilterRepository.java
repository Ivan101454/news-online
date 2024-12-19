package ru.clevertec.newsonline.serviceinteface;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IFilterRepository<E> {

    List<E> filterWord(Map<String, String> filter, Pageable pageable);
}
