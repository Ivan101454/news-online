package ru.clevertec.newsonline.serviceinteface;

import java.util.List;

public interface IFilterRepository<E, F> {

    List<E> filterWord(F f, int pageNumber, int pageSize, Class<E> entityClazz);
}
