package com.flexsolution.chyzhov.kmb.user.filter;

import com.flexsolution.chyzhov.kmb.user.User;
import com.flexsolution.chyzhov.kmb.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserSpecificationBuilder {

    private Long startId;
    private Long endId;
    private String startDate;
    private String endDate;
    private String afterDate;
    private String nameLike;


    public static UserSpecificationBuilder empty() {
        return new UserSpecificationBuilder();
    }

    public UserSpecificationBuilder setStartId(Long id) {
        this.startId = id;
        return this;
    }

    public UserSpecificationBuilder setEndId(Long id) {
        this.endId = id;
        return this;
    }

    public UserSpecificationBuilder setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public UserSpecificationBuilder setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public UserSpecificationBuilder setAfterDate(String date) {
        this.afterDate = date;
        return this;
    }

    public UserSpecificationBuilder setNameLike(String nameLike) {
        this.nameLike = nameLike;
        return this;
    }

    @Nullable
    public Specification<User> build() {
        List<SearchCriteria<?>> params = new ArrayList<>();
        SearchCriteria<?> criteria;

        if (nameLike != null) {
            criteria = new SearchCriteria<String>("username", "like", nameLike);
            params.add(criteria);
        }

        Specification<User> result = userSpecificationBuilder(params);

        if (afterDate != null) {
            UserSpecification spec = new UserSpecification(toLocalDate(afterDate));
            result = Specification.where(result).and(spec);
        }
        if (startId != null && endId != null) {
            UserSpecification spec = new UserSpecification(startId, endId);
            result = Specification.where(result).and(spec);
        }
        if (startDate != null && endDate != null) {
            UserSpecification spec = new UserSpecification(toLocalDate(startDate), toLocalDate(endDate));
            result = Specification.where(result).and(spec);
        }
        return result;
    }

    public Specification<User> userSpecificationBuilder(List<SearchCriteria<?>> params) {
        if (params.size() == 0) {
            return null;
        }
        List<Specification<User>> specs = params.stream()
                .map(UserSpecification::new)
                .collect(Collectors.toList());

        Specification<User> result = specs.get(0);
        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }

    private LocalDate toLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }


}
