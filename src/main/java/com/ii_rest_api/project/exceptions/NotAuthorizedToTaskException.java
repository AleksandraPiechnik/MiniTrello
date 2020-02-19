package com.ii_rest_api.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class NotAuthorizedToTaskException extends Exception {
    public NotAuthorizedToTaskException(String message) {
        super(message);
    }
}
