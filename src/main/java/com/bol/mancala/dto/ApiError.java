package com.bol.mancala.dto;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class ApiError {

    /**
     * Error Status
     */
    private HttpStatus status;
    /**
     * Error Message
     */
    private String message;

    /**
     * Errors
     */
    private List<String> errors;

    /**
     *
     * @param status
     * @param message
     * @param errors
     */
    public ApiError(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    /**
     *
     * @param status
     * @param message
     * @param error
     */
    public ApiError(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }

}
