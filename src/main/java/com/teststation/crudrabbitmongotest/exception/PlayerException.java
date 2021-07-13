package com.teststation.crudrabbitmongotest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class PlayerException extends RuntimeException {

    public PlayerException(final String message) {
        super(message);
    }

    public PlayerException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
