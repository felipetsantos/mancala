package com.ftsantos.mancala.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * not authorized exception
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NotAuthorizedException extends RuntimeException {
    /**
     * @param message
     */
    public NotAuthorizedException(String message) {
        super(message);
    }

}
