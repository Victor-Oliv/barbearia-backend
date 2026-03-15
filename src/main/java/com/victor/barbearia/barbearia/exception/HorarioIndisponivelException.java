package com.victor.barbearia.barbearia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class HorarioIndisponivelException extends RuntimeException {

    public HorarioIndisponivelException(String message) {
        super(message);
    }
}
