package com.ingenico.assignment.ingenicoassignment.controller.dao;

import com.ingenico.assignment.ingenicoassignment.model.Transaction;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a fake in-memory DAO instance for a transaction.
 */
public class TransactionDAO {

    /**
     * Transactions stored in the database.
     */
    private List<Transaction> transactions = new LinkedList<>();

    /**
     * Inserts a transaction in the database.
     *
     * @param transaction Transaction to be inserted.
     */
    public void insert(Transaction transaction) {
        transaction.number = UUID.randomUUID().toString();
        this.transactions.add(transaction);
    }
}
