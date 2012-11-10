package com.alexbool.util;

import org.springframework.http.HttpStatus;

public class StatusCodeException extends RuntimeException {

    private final HttpStatus status;

    public StatusCodeException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
