package com.flexsolution.chyzhov.kmb.user.filter;

import com.flexsolution.chyzhov.kmb.exeption.RestBadRequestException;
import com.flexsolution.chyzhov.kmb.user.User;
import com.flexsolution.chyzhov.kmb.user.filter.SearchCriteria;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;

@NoArgsConstructor
public class UserSpecification implements Specification<User> {

    public UserSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public UserSpecification(LocalDate date) {
        this.fromDate = date;
    }

    public UserSpecification(Long startId, Long endId) {
        this.startId = startId;
        this.endId = endId;
    }

    public UserSpecification(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private Long startId;
    private Long endId;
    private LocalDate startDate;
    private LocalDate endDate;
    private SearchCriteria<?> criteria;
    private LocalDate fromDate;


    @Override
    public Predicate toPredicate
            (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria != null) {
            if (criteria.getOperation().equalsIgnoreCase("after")) {
                return builder.greaterThanOrEqualTo(
                        root.get(criteria.getKey()), criteria.getValue().toString());
            } else if (criteria.getOperation().equalsIgnoreCase("before")) {
                return builder.lessThanOrEqualTo(
                        root.get(criteria.getKey()), criteria.getValue().toString());
            } else if (criteria.getOperation().equalsIgnoreCase("like")) {
                if (root.get(criteria.getKey()).getJavaType() == String.class) {
                    return builder.like(
                            root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
                } else {
                    return builder.equal(root.get(criteria.getKey()), criteria.getValue());
                }
            }
        }
        if (fromDate != null) {
            return builder.greaterThanOrEqualTo(root.get("dateOfBirth"), fromDate);
        }

        if (startDate != null && endDate != null) {
            if (startDate.isBefore(endDate)) {
                return builder.between(root.get("dateOfBirth"), startDate, endDate);
            } else throw new RestBadRequestException("The lastDate must be more then firstDate");
        }
        if (startId != null && endId != null) {
            if (startId < endId) {
                return builder.between(root.get("id"), startId, endId);
            } else throw new RestBadRequestException("The endId must be more then startId");
        }
        return null;
    }


}