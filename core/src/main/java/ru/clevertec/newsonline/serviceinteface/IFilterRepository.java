package ru.clevertec.newsonline.serviceinteface;

import java.util.List;

public interface IFilterRepository<E, F> {

    List<E> filterWord(F f, Class<E> entityClazz, int pageNumber, int pageSize);
}
