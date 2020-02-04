package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoPetFoundException extends RuntimeException {
    public NoPetFoundException(String message) {
        super(message);
    }
}
