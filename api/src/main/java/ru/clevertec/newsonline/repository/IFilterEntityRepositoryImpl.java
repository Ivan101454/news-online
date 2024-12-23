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
import ru.clevertec.newsonline.util.MetaModelUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class IFilterEntityRepositoryImpl<E, F> implements IFilterEntityRepository<E, F>, RepositoryMetadataAccess {

    @PersistenceContext
    private final EntityManager entityManager;

    private final E e;

    @SneakyThrows
    @Override
    public List<E> filterWord(F f, int pageNumber, int pageSize) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteria = (CriteriaQuery<E>) cb.createQuery(e.getClass());
        Root<E> root = (Root<E>) criteria.from(e.getClass());

        Field[] declaredFields = f.getClass().getDeclaredFields();
        HashMap<String, String> filter = new HashMap<>();
        Predicate[] predicates = new Predicate[declaredFields.length];
        int count = 0;


        for (Field field: declaredFields) {
            field.setAccessible(true);
            String name = field.getName();
            String value = (String) field.get(e);

            SingularAttribute<?, String> metaModelAttribite = MetaModelUtil.createMetaModelAttribite(e.getClass(), name, value);
            predicates[count] = cb.like(cb.lower(root.get(String.valueOf(metaModelAttribite))), "%" + value.toLowerCase() + "%");
            count++;
        }

        criteria.select(root).where(cb.and(predicates));
        return entityManager.createQuery(criteria)
                .setFirstResult(pageNumber).setMaxResults(pageSize).getResultList();
    }
}
