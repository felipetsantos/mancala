package com.bol.mancala.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * bad request exception
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {


    /**
     * @param message
     */
    public BadRequestException(String message) {
        super(message);
    }

}
