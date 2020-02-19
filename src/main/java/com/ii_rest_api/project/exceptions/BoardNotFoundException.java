package com.ii_rest_api.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BoardNotFoundException extends Exception {
    public BoardNotFoundException(String message) {
        super(message);
    }
}
