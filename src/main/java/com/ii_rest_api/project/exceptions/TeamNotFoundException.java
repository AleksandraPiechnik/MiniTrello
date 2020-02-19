package com.ii_rest_api.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TeamNotFoundException extends Exception {
    public TeamNotFoundException(String message) {
        super(message);
    }
}
