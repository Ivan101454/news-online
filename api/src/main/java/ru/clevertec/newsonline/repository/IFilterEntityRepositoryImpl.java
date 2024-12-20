package ru.clevertec.newsonline.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.core.support.RepositoryMetadataAccess;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class IFilterEntityRepositoryImpl<E> implements IFilterEntityRepository<E>, RepositoryMetadataAccess {

    @PersistenceContext
    private final EntityManager entityManager;




    @Override
    public List<E> filterWord(Map<String, String> filter, Pageable pageable) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteria = (CriteriaQuery<E>) cb.createQuery(e.getClass());
        Root<E> root = (Root<E>) criteria.from(e.getClass());


        Set<Map.Entry<String, String>> entries = filter.entrySet();
        Predicate[] predicates = new Predicate[entries.size()];
        int count = 0;
        entries.forEach(field -> {
            String key = field.getKey();
            String value = field.getValue();
            String fieldName = e.getClass().getName() + "_." + key;

            predicates[count] = cb.like(cb.lower(root.get(fieldName)), "%" + value.toLowerCase() + "%");
        });


        criteria.select(root).where(cb.and(predicates));
        return entityManager.createQuery(criteria)
                .setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
    }
}
