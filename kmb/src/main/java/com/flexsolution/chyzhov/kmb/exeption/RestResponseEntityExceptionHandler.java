package com.flexsolution.chyzhov.kmb.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {RestBadRequestException.class,})
    protected ResponseEntity<Object> handleConflict(
            RestBadRequestException ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("Exception: %s", ex.getMessage()));
    }
}