package ru.clevertec.newsonline.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.repository.core.support.RepositoryMetadataAccess;
import org.springframework.stereotype.Repository;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.util.MetaModelUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class IFilterEntityRepositoryImpl<E, F> implements IFilterEntityRepository<E, F>, RepositoryMetadataAccess {

    @PersistenceContext
    private final EntityManager entityManager;

    @SneakyThrows
    @Override
    public List<E> filterWord(F f, int pageNumber, int pageSize, Class<E> entityClass) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteria = (CriteriaQuery<E>) cb.createQuery(entityClass);
        Root<E> root = (Root<E>) criteria.from(entityClass);

        Field[] declaredFields = f.getClass().getDeclaredFields();

        Predicate[] predicates = new Predicate[declaredFields.length];
        int count = 0;


        for (Field field: declaredFields) {
            field.setAccessible(true);
            String name = field.getName();
            String value = (String) field.get(f);

            SingularAttribute<E, String> metaModelAttribute = MetaModelUtil.createMetaModelAttribite(entityClass, name, value);
            predicates[count] = cb.like(cb.lower(root.get(metaModelAttribute)), "%" + value.toLowerCase() + "%");
            count++;
        }

        criteria.select(root).where(cb.and(predicates));

        return entityManager.createQuery(criteria)
                .setFirstResult(pageNumber).setMaxResults(pageSize).getResultList();
    }
}
