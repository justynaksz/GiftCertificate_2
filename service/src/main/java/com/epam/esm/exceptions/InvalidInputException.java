package com.epam.esm.exceptions;

/**
 * Exception that should be thrown in case of invalid input.
 */
public class InvalidInputException extends Exception {
    public InvalidInputException() {
        super("At least one of given parameters is invalid");
    }
}
