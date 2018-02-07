package com.ingenico.assignment.ingenicoassignment.controller.exception;

/**
 * Exception related to missing request parameters.
 */
public class RequestParameterMissing extends Exception {

    /**
     * Request parameter that is missing
     */
    private String parameter;

    /**
     * Creates a new exception.
     *
     * @param parameter Request parameter that is missing
     */
    public RequestParameterMissing(String parameter) {
        this.parameter = parameter;
    }

    /**
     * @return Request parameter that is missing
     */
    public String getParameter() {
        return parameter;
    }
}
