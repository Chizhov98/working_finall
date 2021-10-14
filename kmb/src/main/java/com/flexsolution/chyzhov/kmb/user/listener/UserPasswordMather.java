package com.flexsolution.chyzhov.kmb.user.listener;

import com.flexsolution.chyzhov.kmb.exeption.RestBadRequestException;
import com.flexsolution.chyzhov.kmb.user.User;
import com.flexsolution.chyzhov.kmb.user.dto.RequestUserDto;

public class UserPasswordMather {
    public static void passMatcher(RequestUserDto user) {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new RestBadRequestException("passwords must be matched");
        }
    }
}
