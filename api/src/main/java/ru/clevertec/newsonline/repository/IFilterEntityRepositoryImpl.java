package ru.clevertec.newsonline.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.core.support.RepositoryMetadataAccess;
import ru.clevertec.newsonline.serviceinteface.IFilterRepository;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class IFilterEntityRepositoryImpl<E> implements IFilterEntityRepository<E>, RepositoryMetadataAccess {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<E> filterWord(Map<String, String> filter, Pageable pageable) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Comment> criteria = cb.createQuery(Comment.class);
//        Root<Comment> root = criteria.from(Comment.class);
//
//        Set<Map.Entry<String, String>> entries = filter.entrySet();
//        Predicate[] predicates = new Predicate[filter.size()];
//        int count = 0;
//        entries.forEach(x -> {
//            String key = x.getKey();
//            String value = x.getValue();

//            predicates[count] = cb.like(cb.lower(root.get(Comment_.${key})), "%" + value.toLowerCase() + "%")

//        });
//        criteria.select(root).where(cb.and(predicates));
//        return entityManager.createQuery(criteria)
//                .setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        return null;
    }
}
