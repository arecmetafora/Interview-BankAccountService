package com.ingenico.assignment.ingenicoassignment.controller.exception;

/**
 * Exception representing then scenario where an account with a given number was not found.
 */
public class AccountNotFound extends Exception {

    /**
     * Number of account which did not match any account in the system.
     */
    private String accountNumber;

    /**
     * Creates a new exception.
     * @param accountNumber Number of account which did not match any account in the system.
     */
    public AccountNotFound(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * @return Number of account which did not match any account in the system.
     */
    public String getAccountNumber() {
        return accountNumber;
    }
}
