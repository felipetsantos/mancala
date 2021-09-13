package com.bol.mancala.exception;

/**
 * Element not found exception
 */
public class ElementNotFoundException extends RuntimeException {

    public ElementNotFoundException(String message) {
        super(message);
    }
}
