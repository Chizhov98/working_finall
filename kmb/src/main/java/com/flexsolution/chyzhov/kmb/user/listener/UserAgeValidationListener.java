package com.flexsolution.chyzhov.kmb.user.listener;

import com.flexsolution.chyzhov.kmb.exeption.RestBadRequestException;
import com.flexsolution.chyzhov.kmb.user.User;

import javax.persistence.PrePersist;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;

public class UserAgeValidationListener {

    @PrePersist
    public void ageValidation(@Valid User user) {
        LocalDate now = LocalDate.now();
        Period age = Period.between(user.getDateOfBirth(), now);

        if (age.getYears() < 18) {
            throw new RestBadRequestException("Invalid date of birth, user should be at least 18 years old");
        }
    }
}
