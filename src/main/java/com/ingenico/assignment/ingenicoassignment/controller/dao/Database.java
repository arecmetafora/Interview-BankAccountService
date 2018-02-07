package com.ingenico.assignment.ingenicoassignment.controller.dao;

import com.ingenico.assignment.ingenicoassignment.model.Account;
import com.ingenico.assignment.ingenicoassignment.model.Customer;
import com.ingenico.assignment.ingenicoassignment.model.Transaction;

import java.util.List;

/**
 * Represents a fake in-memory database for this assignment.
 */
public class Database {

    /**
     * DAO for Customer entity.
     */
    private static CustomerDAO customerDAO = new CustomerDAO();

    /**
     * DAO for Account entity.
     */
    private static AccountDAO accountDAO = new AccountDAO();

    /**
     * DAO for Transaction entity.
     */
    private static TransactionDAO transactionDAO = new TransactionDAO();

    /**
     * Initializes the Database.
     */
    static {

    }

    /**
     * Checks if a customer exists.
     *
     * @param documentNumber The customer`s document id.
     *
     * @return True if there is a customer with the given document number, or false, otherwise.
     */
    public static boolean customerExists(String documentNumber) {
        return customerDAO.getByDocumentNumber(documentNumber) != null;
    }

    /**
     * Checks if an account exists.
     *
     * @param number The account`s number.
     *
     * @return True if there is an account with the given number, or false, otherwise.
     */
    public static boolean accountExists(String number) {
        return accountDAO.getByNumber(number) != null;
    }

    /**
     * Gets an account by its number.
     *
     * @param number The account`s number.
     *
     * @return The account for a given account number.
     */
    public static Account getAccount(String number) {
        return accountDAO.getByNumber(number);
    }

    /**
     * Inserts a new customer in the database.
     *
     * @param customer The customer to be inserted.
     */
    public static void newCustomer(Customer customer) {
        customerDAO.insert(customer);
    }

    /**
     * Inserts a new account in the database.
     *
     * @param account The account to be inserted.
     */
    public static void newAccount(Account account) {
        accountDAO.insert(account);
    }

    /**
     * Inserts a new transaction in the database
     *
     * @param transaction The transaction to be inserted.
     */
    public static void newTransfer(Transaction transaction) {
        transactionDAO.insert(transaction);
    }

    /**
     * @return All accounts stored in the in-memory database (just for tests)
     */
    public static List<Account> getAllAccounts() {
        return accountDAO.all();
    }
}
