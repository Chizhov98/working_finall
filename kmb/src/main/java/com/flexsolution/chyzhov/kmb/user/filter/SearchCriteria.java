package com.flexsolution.chyzhov.kmb.user.filter;

import lombok.Data;

@Data
public class SearchCriteria<E> {
    private String key;
    private String operation;
    private E value;

    public SearchCriteria(String key, String operation, E value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
}

