package com.ingenico.assignment.ingenicoassignment.controller.exception;

/**
 * Generic exception for business related exceptions.
 */
public class BusinessException extends Exception {

    /**
     * Creates a new exception.
     * @param message
     */
    public BusinessException(String message) {
        super(message);
    }
}
