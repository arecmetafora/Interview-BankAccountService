package com.ingenico.assignment.ingenicoassignment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * Represents a monetary transaction, from one account to another.
 */
public class Transaction {

    /**
     * Creates a new monetary transaction.
     *
     * @param account Account which the transaction was made.
     * @param amount The amount of this transaction.
     * @param description A description to identify this transaction.
     */
    public Transaction(Account account, double amount, String description) {
        this.account = account;
        this.amount = amount;
        this.description = description;
        this.date = new Date();
    }

    /**
     * Transaction number. Constraints will ensure that this number is unique.
     */
    public String number;

    /**
     * Account which this transaction was made.
     */
    @JsonIgnore
    public Account account;

    /**
     * When the transaction was made.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    public Date date;

    /**
     * The amount of this transaction.
     */
    public double amount;

    /**
     * A description to identify this transaction.
     */
    public String description;
}
