package com.flexsolution.chyzhov.kmb.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseBodyDto {

    private int code;
    private Object payload;
    private String message;

    public ResponseBodyDto(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseBodyDto(Object payload, int code) {
        this.code = code;
        this.payload = payload;
    }
}
