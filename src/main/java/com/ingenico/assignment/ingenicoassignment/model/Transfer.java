package com.ingenico.assignment.ingenicoassignment.model;

/**
 * Represents a transfer of money between two bank accounts.
 */
public class Transfer {

    /**
     * Account which the money was debited.
     */
    public String origin;

    /**
     * Account which the money was credited.
     */
    public String destiny;

    /**
     * The amount of the transfer.
     */
    public Double amount;
}
