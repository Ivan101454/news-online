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
import java.util.List;

@RequiredArgsConstructor
public class IFilterEntityRepositoryImpl<E, F> implements IFilterEntityRepository<E, F>, RepositoryMetadataAccess {

    @PersistenceContext
    private final EntityManager entityManager;

    @SneakyThrows
    @Override
    public List<E> filterWord(F f, Class<E> entityClass, int pageNumber, int pageSize) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteria = cb.createQuery(entityClass);
        Root<E> root = criteria.from(entityClass);

        Field[] declaredFields = f.getClass().getDeclaredFields();
        ArrayList<Predicate> listOfPredicates = new ArrayList<>();

        for (Field field: declaredFields) {
            field.setAccessible(true);
            String name = field.getName();
            String value = (String) field.get(f);

            if (value != null) {
                SingularAttribute<E, String> metaModelAttribute = MetaModelUtil.createMetaModelAttribute(entityClass, name);
                listOfPredicates.add(cb.like(cb.lower(root.get(metaModelAttribute)), "%" + value.toLowerCase() + "%"));
            }
        }

        Predicate[] predicates = new Predicate[listOfPredicates.size()];
        criteria.select(root).where(cb.and(listOfPredicates.toArray(predicates)));

        return entityManager.createQuery(criteria)
                .setFirstResult(pageNumber).setMaxResults(pageSize).getResultList();
    }
}
