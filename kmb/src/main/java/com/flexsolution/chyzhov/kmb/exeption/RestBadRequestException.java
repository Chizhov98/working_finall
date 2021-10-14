package com.flexsolution.chyzhov.kmb.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RestBadRequestException extends RuntimeException {

    public RestBadRequestException(String message) {
        super(message);
    }
}
