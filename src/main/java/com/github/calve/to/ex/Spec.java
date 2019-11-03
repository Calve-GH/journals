package com.github.calve.to.ex;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class Spec<T> implements Specification<T> {

    private final List<SearchCriteria> criteriaList;

    public Spec(List<SearchCriteria> criteria) {
        this.criteriaList = criteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria criteria : criteriaList) {
            try {
                if (root.get(criteria.getKey()).getJavaType() == String.class) {
                    predicates.add(builder.like(
                            root.get(criteria.getKey()), "%" + criteria.getValue() + "%"));
                } else {
                    predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
                }
            } catch (Exception e) {
                System.out.println(criteria.getKey() + " " + criteria.getValue()); //todo sout
            }
        }
        return builder.or(predicates.toArray(new Predicate[0]));
    }
}
