package com.flexsolution.chyzhov.kmb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class AbstractMapper {


    @Value("${date.format}")
    private String DATE_FORMAT_PATTERN;

    public LocalDate toLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
        return LocalDate.parse(date, formatter);
    }

    public String fromLocalDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
        return formatter.format(date);
    }



    public Map<String, String> returnMapErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        List<FieldError> errors = result.getFieldErrors();
        for (FieldError error : errors) {
            map.put(error.getField(), error.getDefaultMessage());
        }
        return map;
    }
}
