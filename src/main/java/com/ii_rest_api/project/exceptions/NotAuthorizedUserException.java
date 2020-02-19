package com.ii_rest_api.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class NotAuthorizedUserException extends Exception {
    public NotAuthorizedUserException(String message) {
        super(message);
    }
}