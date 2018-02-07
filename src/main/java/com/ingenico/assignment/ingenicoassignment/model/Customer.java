package com.ingenico.assignment.ingenicoassignment.model;

/**
 * Represents a customer from the Bank.
 */
public class Customer {

    /**
     * Customer`s document number. Constraints will ensure that this number is unique.
     */
    public String documentNumber;

    /**
     * Customer`s first name.
     */
    public String firstName;

    /**
     * Customer`s last name.
     */
    public String lastName;

    /**
     * Customer`s phone number.
     */
    public String phoneNumber;

    /**
     * Customer`s email.
     */
    public String email;

    /**
     * Customer`s address.
     */
    public String address;
}
