package com.ingenico.assignment.ingenicoassignment.controller.dao;

import com.ingenico.assignment.ingenicoassignment.model.Account;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a fake in-memory DAO instance for an account.
 */
public class AccountDAO {

    /**
     * Identity counter for Account entity.
     */
    private int identityCounter = 1000;

    /**
     * Accounts stored in the database.
     */
    private List<Account> accounts = new LinkedList<>();

    /**
     * Index to fast-access an account by its number.
     */
    private HashMap<String, Account> accountNumberIndex = new HashMap<>();

    /**
     * Inserts an account in the database.
     *
     * @param account Account to be inserted.
     */
    public void insert(Account account) {
        account.number = String.format("%d-%d", identityCounter++, (int)(Math.random()*10));
        this.accounts.add(account);
        this.accountNumberIndex.put(account.number, account);
    }

    /**
     * Gets an account by its number.
     *
     * @param number Account number.
     *
     * @return The account for a given number.
     */
    Account getByNumber(String number) {
        return accountNumberIndex.get(number);
    }

    /**
     * @return All accounts stored in the in-memory database (just for tests)
     */
    List<Account> all() {
        return accounts;
    }
}
