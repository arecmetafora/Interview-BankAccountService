package com.ingenico.assignment.ingenicoassignment.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a bank account.
 */
public class Account {

    /**
     * The bank account number. Constraints will ensure that this number is unique.
     */
    public String number;

    /**
     * The account`s owner (either a person name or a company name).
     */
    public Customer owner;

    /**
     * Type of the bank account.
     */
    public AccountType type;

    /**
     * The current account balance.
     */
    public Double balance;

    /**
     * List of transactions for this account.
     */
    public List<Transaction> transactions = new LinkedList<>();
}
