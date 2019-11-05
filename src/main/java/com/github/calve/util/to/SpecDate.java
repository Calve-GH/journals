package com.github.calve.util.to;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpecDate<T> implements Specification<T> {

    private final DateCriteria from;
    private final DateCriteria to;

    public SpecDate(DateCriteria from, DateCriteria to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(to)) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(from.getKey()), from.getValue()));
            predicates.add(builder.lessThanOrEqualTo(root.get(to.getKey()), to.getValue()));
        } else {
            predicates.add(builder.equal(root.get(from.getKey()), from.getValue()));
        }
        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
