package com.bol.mancala.exception;

import com.bol.mancala.dto.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseEntity<ApiError> handleAllBadRequestExceptions(BadRequestException ex, WebRequest request) {
        return this.handleCustomExceptions(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ElementNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ApiError> handleAllElementNotFoundExceptions(ElementNotFoundException ex, WebRequest request) {
        return this.handleCustomExceptions(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<ApiError> handleAllElementNotFoundExceptions(NotAuthorizedException ex, WebRequest request) {
        return this.handleCustomExceptions(ex, request, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllOtherExceptions(Exception ex, WebRequest request) {
        return this.handleCustomExceptions(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ApiError> handleCustomExceptions(Exception ex, WebRequest request, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ApiError(httpStatus.value(), ex.getMessage()), httpStatus);
    }


}
