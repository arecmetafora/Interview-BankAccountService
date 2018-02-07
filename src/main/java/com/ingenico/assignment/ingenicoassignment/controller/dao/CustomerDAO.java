package com.ingenico.assignment.ingenicoassignment.controller.dao;

import com.ingenico.assignment.ingenicoassignment.model.Customer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a fake in-memory DAO instance for a customer.
 */
public class CustomerDAO {

    /**
     * Customers stored in the database.
     */
    private List<Customer> customers = new LinkedList<>();

    /**
     * Index to fast-access a customer by its document number.
     */
    private HashMap<String, Customer> documentNumberIndex = new HashMap<>();

    /**
     * Inserts a customer in the database.
     *
     * @param customer Customer to be inserted.
     */
    void insert(Customer customer) {
        this.customers.add(customer);
        this.documentNumberIndex.put(customer.documentNumber, customer);
    }

    /**
     * Gets a customer by its document number.
     *
     * @param documentNumber Customer`s document number.
     *
     * @return The customer for a given document number.
     */
    Customer getByDocumentNumber(String documentNumber) {
        return documentNumberIndex.get(documentNumber);
    }
}
