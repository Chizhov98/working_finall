package com.flexsolution.chyzhov.kmb.user;


import com.flexsolution.chyzhov.kmb.exeption.RestBadRequestException;
import com.flexsolution.chyzhov.kmb.user.dto.RequestUserDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.Period;

@Component(value = "userValidator")
public class UserValidator implements Validator {
    private final int MIN_USERNAME_LENGTH = 5;
    private final int MIN_PASSWORD_LENGTH = 6;
    private final int MAX_PASSWORD_LENGTH = 20;
    private final UserMapper mapper;

    public UserValidator(UserMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(RequestUserDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestUserDto user = (RequestUserDto) target;

        if (user.getUsername() != null) {
            if (user.getUsername().length() < MIN_USERNAME_LENGTH) {
                errors.reject(String.format("username", "username can no be less then %d", MIN_USERNAME_LENGTH));
            }
        } else errors.reject("username can not be null");

        if (user.getPassword() != null) {
            if (user.getPassword().length() > MAX_PASSWORD_LENGTH
                    || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
                errors.reject(String.format("password", "password length must be %d or more and less then %d",
                        MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH));
            }
            if (user.getPassword().matches(".*[A-Z].*")) {
                errors.reject("password", "Password must exist at least one capital letter");
            }
            if (user.getPassword().matches(".*[a-z].*")) {
                errors.reject("password", "Password must exist at least one digit letter");
            }
            if (user.getPassword().matches(".*\\d.*")) {
                errors.reject("password", "Password must exist at least one number");
            }
            if (user.getConfirmPassword() == null
                    || user.getPassword().equals(user.getConfirmPassword())) {

                errors.reject("password", "Passwords must be matching");
            }
        } else errors.reject("password", "Passwords can`t be null");

        if (user.getDateOfBirth() != null) {
            LocalDate now = LocalDate.now();
            Period age = Period.between(mapper.toLocalDate(user.getDateOfBirth()), now);
            if (age.getYears() < 18) {
                errors.reject("dateOfBirth", "Invalid date of birth, user should be at least 18 years old");
            }
        } else errors.reject("dateOfBirth", "Date of birth can`t be null");
    }

}
